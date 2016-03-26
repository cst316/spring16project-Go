/**
 * Item.java
 * Created on 3.22.2016, 16:58:13 Alex
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
/*$Id: Task.java,v 1.9 2005/06/16 04:21:32 alexeya Exp $*/
public interface Item {
    
    public static final int SCHEDULED = 0;

    public static final int ACTIVE = 1;

    public static final int COMPLETED = 2;

    public static final int FROZEN = 4;

    public static final int FAILED = 5;
    
    public static final int LOCKED = 6;
    
    public static final int DEADLINE = 7;
    
    public static final int PRIORITY_LOWEST = 0;
    
    public static final int PRIORITY_LOW = 1;
    
    public static final int PRIORITY_NORMAL = 2;
    
    public static final int PRIORITY_HIGH = 3;
    
    public static final int PRIORITY_HIGHEST = 4;
    
    CalendarDate getStartDate();
    void setStartDate(CalendarDate date);

    CalendarDate getEndDate();
    void setEndDate(CalendarDate date);
    
    int getStatus(CalendarDate date);
    
    int getProgress();
    void setProgress(int p);
    
    int getPriority();
    void setPriority(int p);
    
    String getID();
    
    String getText();
    void setText(String s);
    
    /*Collection getDependsFrom();
    
    void addDependsFrom(Task task);
    
    void removeDependsFrom(Task task);*/
            
    Collection getSubItems();    
    Item getSubItems(String id);
    
    boolean hasSubItems(String id);
    
    void setEffort(long effort);
    long getEffort();
    
    void setDescription(String description);
    String getDescription();

    Item getParentItem();
    String getParentId();
    
    void freeze();
    void unfreeze();
	long getRate();
    
    nu.xom.Element getContent();
}
