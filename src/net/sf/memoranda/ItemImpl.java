/**
 * DefaultItem.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;
import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 *
 */
/*$Id: ItemImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
public class ItemImpl implements Item, Comparable {

    private Element _element = null;
    private ItemList _tl = null;

    /**
     * Constructor for DefaultItem.
     */
    public ItemImpl(Element itemElement, ItemList tl) {
        _element = itemElement;
        _tl = tl;
    }

    public Element getContent() {
        return _element;
    }

    public CalendarDate getStartDate() {
        return new CalendarDate(_element.getAttribute("startDate").getValue());
    }

    public void setStartDate(CalendarDate date) {
           setAttr("startDate", date.toString());
    }

    public CalendarDate getEndDate() {
		String ed = _element.getAttribute("endDate").getValue();
		if (ed != "")
			return new CalendarDate(_element.getAttribute("endDate").getValue());
		Item parent = this.getParentItem();
		if (parent != null)
			return parent.getEndDate();
		Project pr = this._tl.getProject();
		if (pr.getEndDate() != null)
			return pr.getEndDate();
		return this.getStartDate();
        
    }

    public void setEndDate(CalendarDate date) {
		if (date == null)
			setAttr("endDate", "");
		setAttr("endDate", date.toString());
    }

    public long getEffort() {
    	Attribute attr = _element.getAttribute("effort");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Long.parseLong(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }

    public void setEffort(long effort) {
        setAttr("effort", String.valueOf(effort));
    }
	
	/* 
	 * @see net.sf.memoranda.Item#getParentItem()
	 */
	public Item getParentItem() {
		Node parentNode = _element.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("item")) 
        	    return new ItemImpl(parent, _tl);
    	}
    	return null;
	}
	
	public String getParentItemId() {
		Item parent = this.getParentItem();
		if (parent != null)
			return parent.getID();
		return null;
	}

    public String getDescription() {
    	Element thisElement = _element.getFirstChildElement("description");
    	if (thisElement == null) {
    		return null;
    	}
    	else {
       		return thisElement.getValue();
    	}
    }

    public void setDescription(String s) {
    	Element desc = _element.getFirstChildElement("description");
    	if (desc == null) {
        	desc = new Element("description");
            desc.appendChild(s);
            _element.appendChild(desc);    	
    	}
    	else {
            desc.removeChildren();
            desc.appendChild(s);    	
    	}
    }

    /**s
     * @see net.sf.memoranda.Item#getStatus()
     */
    public int getStatus(CalendarDate date) {
        CalendarDate start = getStartDate();
        CalendarDate end = getEndDate();
        if (isFrozen())
            return Item.FROZEN;
        if (isCompleted())
                return Item.COMPLETED;
        
		if (date.inPeriod(start, end)) {
            if (date.equals(end))
                return Item.DEADLINE;
            else
                return Item.ACTIVE;
        }
		else if(date.before(start)) {
				return Item.SCHEDULED;
		}
		
		if(start.after(end)) {
			return Item.ACTIVE;
		}

        return Item.FAILED;
    }
    /**
     * Method isDependsCompleted.
     * @return boolean
     */
/*
    private boolean isDependsCompleted() {
        Vector v = (Vector) getDependsFrom();
        boolean check = true;
        for (Enumeration en = v.elements(); en.hasMoreElements();) {
            Task t = (Task) en.nextElement();
            if (t.getStatus() != Task.COMPLETED)
                check = false;
        }
        return check;
    }
*/
    private boolean isFrozen() {
        return _element.getAttribute("frozen") != null;
    }

    private boolean isCompleted() {
        return getProgress() == 100;
    }

    /**
     * @see net.sf.memoranda.Item#getID()
     */
    public String getID() {
        return _element.getAttribute("id").getValue();
    }

    /**
     * @see net.sf.memoranda.Item#getText()
     */
    public String getText() {
        return _element.getFirstChildElement("text").getValue();
    }

    public String toString() {
        return getText();
    }
    
    /**
     * @see net.sf.memoranda.Item#setText()
     */
    public void setText(String s) {
        _element.getFirstChildElement("text").removeChildren();
        _element.getFirstChildElement("text").appendChild(s);
    }

    /**
     * @see net.sf.memoranda.Item#freeze()
     */
    public void freeze() {
        setAttr("frozen", "yes");
    }

    /**
     * @see net.sf.memoranda.Item#unfreeze()
     */
    public void unfreeze() {
        if (this.isFrozen())
            _element.removeAttribute(new Attribute("frozen", "yes"));
    }

    /**
     * @see net.sf.memoranda.Item#getDependsFrom()
     */
    public Collection getDependsFrom() {
        Vector v = new Vector();
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            Item t = _tl.getItem(id);
            if (t != null)
                v.add(t);
        }
        return v;
    }
    /**
     * @see net.sf.memoranda.Item#addDependsFrom(net.sf.memoranda.Item)
     */
    public void addDependsFrom(Item item) {
        Element dep = new Element("dependsFrom");
        dep.addAttribute(new Attribute("idRef", item.getID()));
        _element.appendChild(dep);
    }
    /**
     * @see net.sf.memoranda.Item#removeDependsFrom(net.sf.memoranda.Item)
     */
    public void removeDependsFrom(Item item) {
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            if (id.equals(item.getID())) {
                _element.removeChild(deps.get(i));
                return;
            }
        }
    }
    /**
     * @see net.sf.memoranda.Item#getProgress()
     */
    public int getProgress() {
        return new Integer(_element.getAttribute("progress").getValue()).intValue();
    }
    /**
     * @see net.sf.memoranda.Item#setProgress(int)
     */
    public void setProgress(int p) {
        if ((p >= 0) && (p <= 100))
            setAttr("progress", new Integer(p).toString());
    }
    /**
     * @see net.sf.memoranda.Item#getPriority()
     */
    public int getPriority() {
        Attribute pa = _element.getAttribute("priority");
        if (pa == null)
            return Item.PRIORITY_NORMAL;
        return new Integer(pa.getValue()).intValue();
    }
    /**
     * @see net.sf.memoranda.Item#setPriority(int)
     */
    public void setPriority(int p) {
        setAttr("priority", String.valueOf(p));
    }

    private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null)
           _element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }

	/**
	 * An "Item rate" is an informal index of importance of the Item
	 * considering priority, number of days to deadline and current 
	 * progress. 
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 * @param CalendarDate
	 * @return long
	 */

	private long calcItemRate(CalendarDate d) {
		Calendar endDateCal = getEndDate().getCalendar();
		Calendar dateCal = d.getCalendar();
		int numOfDays = (endDateCal.get(Calendar.YEAR)*365 + endDateCal.get(Calendar.DAY_OF_YEAR)) - 
						(dateCal.get(Calendar.YEAR)*365 + dateCal.get(Calendar.DAY_OF_YEAR));
		if (numOfDays < 0) return -1; //Something wrong ?
		return (100-getProgress()) / (numOfDays+1) * (getPriority()+1);
	}

    /**
     * @see net.sf.memoranda.Item#getRate()
     */
	 
     public long getRate() {
/*	   Task t = (Task)task;
	   switch (mode) {
		   case BY_IMP_RATE: return -1*calcTaskRate(t, date);
		   case BY_END_DATE: return t.getEndDate().getDate().getTime();
		   case BY_PRIORITY: return 5-t.getPriority();
		   case BY_COMPLETION: return 100-t.getProgress();
	   }
       return -1;
*/
		return -1*calcItemRate(CurrentDate.get());
	 }
	   
	 /*
	  * Comparable interface
	  */
	  
	 public int compareTo(Object o) {
		 Item item = (Item) o;
		 	if(getRate() > item.getRate())
				return 1;
			else if(getRate() < item.getRate())
				return -1;
			else 
				return 0;
	 }
	 
	 public boolean equals(Object o) {
	     return ((o instanceof Item) && (((Item)o).getID().equals(this.getID())));
	 }

	/* 
	 * @see net.sf.memoranda.Item#getSubItems()
	 */
	public Collection getSubItems() {
		Elements subItems = _element.getChildElements("item");
            return convertToItemObjects(subItems);
	}

	private Collection convertToItemObjects(Elements items) {
        Vector v = new Vector();
        for (int i = 0; i < items.size(); i++) {
            Item t = new ItemImpl(items.get(i), _tl);
            v.add(t);
        }
        return v;
    }
	
	/* 
	 * @see net.sf.memoranda.Item#getSubItem(java.lang.String)
	 */
	public Item getSubItem(String id) {
		Elements subItems = _element.getChildElements("item");
		for (int i = 0; i < subItems.size(); i++) {
			if (subItems.get(i).getAttribute("id").getValue().equals(id))
				return new ItemImpl(subItems.get(i), _tl);
		}
		return null;
	}

	/* 
	 * @see net.sf.memoranda.Item#hasSubItems()
	 */
	public boolean hasSubItems(String id) {
		Elements subItems = _element.getChildElements("item");
		for (int i = 0; i < subItems.size(); i++) 
			if (subItems.get(i).getAttribute("id").getValue().equals(id))
				return true;
		return false;
	}

	
}
