/**
 * ItemList.java
 * Created on 21.02.2003, 12:25:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;
import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;
/**
 * 
 */
/*$Id: ItemList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface ItemList {

	Project getProject();
    Item getItem(String id);

    Item createItem(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, String description, String parentItemId);

    void removeItem(Item item);

    public boolean hasSubItems(String id);
    
	public boolean hasParentItem(String id);

	public Collection getTopLevelItems();
	
    public Collection getAllSubItems(String itemId);
    public Collection getActiveSubItems(String itemId,CalendarDate date);
    
//    public void adjustParentTasks(Task t);
    
    public long calculateTotalEffortFromSubItems(Item t);
    public CalendarDate getLatestEndDateFromSubItems(Item t);
    public CalendarDate getEarliestStartDateFromSubItems(Item t);
    public long[] calculateCompletionFromSubItems(Item t);

    nu.xom.Document getXMLContent();

}
