/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
//import nu.xom.converters.*;
//import org.apache.xerces.dom.*;
//import nux.xom.xquery.XQueryUtil;

/**
 * 
 */
/*$Id: ItemListImpl.java,v 1.14 2006/07/03 11:59:19 alexeya Exp $*/
public class ItemListImpl implements ItemList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
	
	/*
	 * Hastable of "item" XOM elements for quick searching them by ID's
	 * (ID => element) 
	 */
	private Hashtable elements = new Hashtable();
    
    /**
     * Constructor for ItemListImpl.
     */
    public ItemListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
		buildElements(_root);
    }
    
    public ItemListImpl(Project prj) {            
            _root = new Element("itemlist");
            _doc = new Document(_root);
            _project = prj;
    }
    
	public Project getProject() {
		return _project;
	}
		
	/*
	 * Build the hashtable recursively
	 */
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("item");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
		}
	}
	
    /**
     * All methods to obtain list of items are consolidated under getAllSubItems and getActiveSubItems.
     * If a root item is required, just send a null ItemId
     */
    public Collection getAllSubItems(String itemId) {
    	if ((itemId == null) || (itemId.length() == 0)) {
    		return getAllRootItems();
    	}
    	else {
            Element item = getItemElement(itemId);
            if (item == null)
                return new Vector();
            Elements subItems = item.getChildElements("item");
            return convertToItemObjects(subItems);    	    		
    	}
    }
    
    public Collection getTopLevelItems() {
        return getAllRootItems();
    }

    /**
     * All methods to obtain list of items are consolidated under getAllSubItems and getActiveSubItems.
     * If a root item is required, just send a null itemId
     */
    public Collection getActiveSubItems(String itemId,CalendarDate date) {
        Collection allItems = getAllSubItems(itemId);        
        return filterActiveItems(allItems,date);
    }

    public Item createItem(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, String description, String parentItemId) {
        Element el = new Element("item");
        el.addAttribute(new Attribute("startDate", startDate.toString()));
        el.addAttribute(new Attribute("endDate", endDate != null? endDate.toString():""));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        el.addAttribute(new Attribute("progress", "0"));
        el.addAttribute(new Attribute("effort", String.valueOf(effort)));
        el.addAttribute(new Attribute("priority", String.valueOf(priority)));
                
        Element txt = new Element("text");
        txt.appendChild(text);
        el.appendChild(txt);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);

        if (parentItemId == null) {
            _root.appendChild(el);
        }
        else {
            Element parent = getItemElement(parentItemId);
            parent.appendChild(el);
        }
        
		elements.put(id, el);
		
        Util.debug("Created item with parent " + parentItemId);
        
        return new ItemImpl(el, this);
    }
	
	/**
     * @see net.sf.memoranda.ItemList#removeItem(import net.sf.memoranda.Item)
     */

    public void removeItem(Item item) {
        String parentItemId = item.getParentItemId();
        if (parentItemId == null) {
            _root.removeChild(item.getContent());            
        }
        else {
            Element parentNode = getItemElement(parentItemId);
            parentNode.removeChild(item.getContent());
        }
		elements.remove(item.getID());
    }

    public boolean hasSubItems(String id) {
        Element item = getItemElement(id);
        if (item == null) return false;
        if(item.getChildElements("item").size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Item getItem(String id) {
        Util.debug("Getting item " + id);          
        return new ItemImpl(getItemElement(id), this);          
    }
    
    public boolean hasParentItem(String id) {
    	Element t = getItemElement(id);
    	
    	Node parentNode = t.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("item")) {
        	    return true;
        	}
        	else {
        	    return false;
        	}
    	}
    	else {
    	    return false;
    	}
    }

    /**
     * @see net.sf.memoranda.ItemList#getXMLContent()
     */	 
    public Document getXMLContent() {
        return _doc;
    }
    
    /**
     * Recursively calculate total effort based on subitems for every node in the item tree
     * The values are saved as they are calculated as well
     * 
     * @param t
     * @return
     */
    public long calculateTotalEffortFromSubItems(Item t) {
        long totalEffort = 0;
        if (hasSubItems(t.getID())) {
            Collection subItems = getAllSubItems(t.getID());
            for (Iterator iter = subItems.iterator(); iter.hasNext();) {
            	Item e = (Item) iter.next();
            	totalEffort = totalEffort + calculateTotalEffortFromSubItems(e);
            }
            t.setEffort(totalEffort);
            return totalEffort;            
        }
        else {
            return t.getEffort();
        }
    }

    /**
     * Looks through the entire sub item tree and corrects any inconsistencies in start dates
     * 
     * @param t
     * @return
     */
    public CalendarDate getEarliestStartDateFromSubItems(Item t) {
        CalendarDate d = t.getStartDate();
        if (hasSubItems(t.getID())) {
	        Collection subItems = getAllSubItems(t.getID());
	        for (Iterator iter = subItems.iterator(); iter.hasNext();) {
	        	Item e = (Item) iter.next();
	        	CalendarDate dd = getEarliestStartDateFromSubItems(e);
	        	if(dd.before(d)) {
	        	    d = dd;
	        	}
	        }
	        t.setStartDate(d);
	        return d;
        }
        else {
            return t.getStartDate();
        }
    }

    /**
     * Looks through the entire sub item tree and corrects any inconsistencies in start dates
     * 
     * @param t
     * @return
     */
    public CalendarDate getLatestEndDateFromSubItems(Item t) {
        CalendarDate d = t.getEndDate();
        if (hasSubItems(t.getID())) {
	        Collection subItems = getAllSubItems(t.getID());
	        for (Iterator iter = subItems.iterator(); iter.hasNext();) {
	        	Item e = (Item) iter.next();
	        	CalendarDate dd = getLatestEndDateFromSubItems(e);
	        	if(dd.after(d)) {
	        	    d = dd;
	        	}
	        }
	        t.setEndDate(d);
	        return d;
        }
        else {
            return t.getEndDate();
        }
    }
    
    /**
     * Looks through the entire sub item tree and calculates progress on all parent item nodes
     * 
     * @param t
     * @return long[] of size 2. First long is expended effort in milliseconds, 2nd long is total effort in milliseconds
     */
    public long[] calculateCompletionFromSubItems(Item t) {
//        Util.debug("Task " + t.getText());
        
        long[] res = new long[2];
        long expendedEffort = 0; // milliseconds
        long totalEffort = 0; // milliseconds
        if (hasSubItems(t.getID())) {
            Collection subItems = getAllSubItems(t.getID());
            for (Iterator iter = subItems.iterator(); iter.hasNext();) {
            	Item e = (Item) iter.next();
            	long[] subItemCompletion = calculateCompletionFromSubItems(e);
            	expendedEffort = expendedEffort + subItemCompletion[0];
            	totalEffort = totalEffort + subItemCompletion[1];
            }
            
            int thisProgress = (int) Math.round((((double)expendedEffort / (double)totalEffort) * 100));
            t.setProgress(thisProgress);

//            Util.debug("Expended Effort: "+ expendedEffort);
//            Util.debug("Total Effort: "+ totalEffort);
//            Util.debug("Progress: "+ t.getProgress());

            res[0] = expendedEffort;
            res[1] = totalEffort;
            return res;            
        }
        else {
            long eff = t.getEffort();
            // if effort was not filled in, it is assumed to be "1 hr" for the purpose of calculation
            if (eff == 0) {
                eff = 1;
            }
            res[0] = Math.round((double)(t.getProgress()* eff) / 100d); 
            res[1] = eff;
            return res;
        }
    }    
    /*
     * private methods below this line
     */
    private Element getItemElement(String id) {
               
		/*Nodes nodes = XQueryUtil.xquery(_doc, "//task[@id='" + id + "']");
        if (nodes.size() > 0) {
            Element el = (Element) nodes.get(0);
            return el;            
        }
        else {
            Util.debug("Task " + id + " cannot be found in project " + _project.getTitle());
            return null;
        } */
		Element el = (Element)elements.get(id);
		if (el == null) {
			Util.debug("Item " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
    }
    
    private Collection getAllRootItems() {
        Elements items = _root.getChildElements("item");
        return convertToItemObjects(items);    	    		
    }
    
    private Collection convertToItemObjects(Elements items) {
        Vector v = new Vector();

        for (int i = 0; i < items.size(); i++) {
            Item t = new ItemImpl(items.get(i), this);
            v.add(t);
        }
        return v;
    }

    private Collection filterActiveItems(Collection items,CalendarDate date) {
        Vector v = new Vector();
        for (Iterator iter = items.iterator(); iter.hasNext();) {
            Item t = (Item) iter.next();
            if(isActive(t,date)) {
                v.add(t);
            }
        }
        return v;
    }

    private boolean isActive(Item t,CalendarDate date) {
    	if ((t.getStatus(date) == Item.ACTIVE) || (t.getStatus(date) == Item.DEADLINE) || (t.getStatus(date) == Item.FAILED)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /*
     * deprecated methods below
     * 
     */
                    
//    public void adjustParentTasks(Task t) {
//    	if ((t.getParent() == null) || (t.getParent().equals(""))){
//    		return;
//    	}
//    	else {
//    		Task p = getTask(t.getParent());
//    		
//    		long totalEffort = calculateTotalEffortFromSubTasks(p);
//    		
//    		if(totalEffort > p.getEffort()) {
//    			p.setEffort(totalEffort);
//    		}
//    		if(t.getStartDate().before(p.getStartDate())) {
//    			p.setStartDate(t.getStartDate());
//    		}
//    		if(t.getEndDate().after(p.getEndDate())) {
//    			p.setEndDate(t.getEndDate());
//    		}
//    		
//        	if (!((p.getParent() == null) || (p.getParent().equals("")))){
//        		// still has parent, go up the tree
//        		adjustParentTasks(p);
//        	}    		
//    	}
//    }
}
