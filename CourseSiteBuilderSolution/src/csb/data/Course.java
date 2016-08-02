package csb.data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a course to be edited and then used to
 * generate a site.
 * 
 * @author Richard McKenna
 */
public class Course {
    // THESE COURSE DETAILS DESCRIBE WHAT'S REQUIRED BY
    // THE COURSE SITE PAGES
    Subject subject;
    int number;
    String title;
    Semester semester;
    int year;
    Instructor instructor;
    LocalDate startingMonday;
    LocalDate endingFriday;
    List<CoursePage> pages;
    List<DayOfWeek> lectureDays;

    // THESE ARE THE THINGS WE'LL PUT IN OUR SCHEDULE PAGE
    ObservableList<ScheduleItem> scheduleItems;
    ObservableList<Lecture> lectures;
    ObservableList<Assignment> assignments;

    /**
     * Constructor for setting up a Course, it initializes the 
     * Instructor, which would have already been loaded from a file.
     * 
     * @param initInstructor The instructor for this course. Note that
     * this can be changed by getting the Instructor and then calling
     * mutator methods on it.
     */
    public Course(Instructor initInstructor) {
        // INITIALIZE THIS OBJECT'S DATA STRUCTURES
        pages = new ArrayList();
        lectureDays = new ArrayList();
        
        // AND KEEP THE INSTRUCTOR
        instructor = initInstructor;
        
        // INIT THE SCHEDULE STUFF
        scheduleItems = FXCollections.observableArrayList();
        lectures = FXCollections.observableArrayList();
        assignments = FXCollections.observableArrayList();
    }

    // BELOW ARE ALL THE ACCESSOR METHODS FOR A COURSE
    // AND THE MUTATOR METHODS. NOTE THAT WE'LL NEED TO CALL
    // THESE AS USERS INPUT VALUES IN THE GUI
     
    public boolean hasCoursePage(CoursePage testPage) {
        return pages.contains(testPage);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public LocalDate getStartingMonday() {
        return startingMonday;
    }

    public void setStartingMonday(LocalDate startingMonday) {
        this.startingMonday = startingMonday;
    }

    public LocalDate getEndingFriday() {
        return endingFriday;
    }
    
    public void setEndingFriday(LocalDate endingFriday) {
        this.endingFriday = endingFriday;
    }
    
    public void setScheduleDates(LocalDate initStartingMonday, LocalDate initEndingFriday) {
        setStartingMonday(initStartingMonday);
        setEndingFriday(initEndingFriday);
    }
        
    public void addPage(CoursePage pageToAdd) {
        pages.add(pageToAdd);
    }
    
    public List<CoursePage> getPages() {
        return pages;
    }
    
    public void selectPage(CoursePage coursePage) {
        if (!pages.contains(coursePage))
            pages.add(coursePage);
    }
    
    public void unselectPage(CoursePage coursePage) {
        if (pages.contains(coursePage))
            pages.remove(coursePage);
    }

    public List<DayOfWeek> getLectureDays() {
        return lectureDays;
    }
    
    // BELOW ARE ADDITIONAL METHODS FOR UPDATING A COURSE
    
    public void selectLectureDay(DayOfWeek dayOfWeek) {
        if (!lectureDays.contains(dayOfWeek))
            lectureDays.add(dayOfWeek);
        else
            lectureDays.remove(dayOfWeek);
    }
    
    public void selectLectureDay(DayOfWeek dayOfWeek, boolean isSelected) {
        if (isSelected) {
            if (!lectureDays.contains(dayOfWeek))
                lectureDays.add(dayOfWeek);
        }
        else {
            lectureDays.remove(dayOfWeek);
        }
    }

    public void clearPages() {
        pages.clear();
    }

    public void clearLectureDays() {
        lectureDays.clear();
    }
    
    public void clearScheduleItems() {
        scheduleItems.clear();
    }
    
    public void clearLectures() {
        lectures.clear();
    }
    
    public void clearHWs() {
        assignments.clear();
    }

    public void addLectureDay(DayOfWeek dayOfWeek) {
        lectureDays.add(dayOfWeek);
    }

    public boolean hasLectureDay(DayOfWeek dayOfWeek) {
        return lectureDays.contains(dayOfWeek);
    }
    
    public void addScheduleItem(ScheduleItem si) {
        scheduleItems.add(si);
        Collections.sort(scheduleItems);
    }
    
    public ObservableList<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void removeScheduleItem(ScheduleItem itemToRemove) {
        scheduleItems.remove(itemToRemove);
    }

    public void addLecture(Lecture l) {
        lectures.add(l);
    }
    
    public ObservableList<Lecture> getLectures() {
        return lectures;
    }
    
    public void removeLecture(Lecture lectureToRemove) {
        lectures.remove(lectureToRemove);
    }
    
    public void moveUpLecture(Lecture lectureToMoveUp) {
        int index = lectures.indexOf(lectureToMoveUp);
        if (index > 0) {
            Lecture temp = lectures.get(index - 1);
            lectures.set(index-1, lectureToMoveUp);
            lectures.set(index, temp);
        }
    }
    
    public void moveDownLecture(Lecture lectureToMoveDown){
        int index = lectures.indexOf(lectureToMoveDown);
        if (index < (lectures.size()-1)) {
            Lecture temp = lectures.get(index + 1);
            lectures.set(index+1, lectureToMoveDown);
            lectures.set(index, temp);
        }
    }
    
    public void addAssignment(Assignment a) {
        assignments.add(a);
        Collections.sort(assignments);
    }
    
    public ObservableList<Assignment> getAssignments() {
        return assignments;
    }
    
    public void removeAssignment(Assignment assignmentToRemove) {
        assignments.remove(assignmentToRemove);
    }
    
    public ScheduleItem getScheduleItemForDate(LocalDate date) {
        for (ScheduleItem scheduleItem : scheduleItems) {
            if (scheduleItem.getDate().equals(date))
                return scheduleItem;
        }
        return null;
    }
    
    public HashMap<LocalDate, ScheduleItem> getScheduleItemMappings() {
        // GET THE SCHEDULE ITEM DATES FOR QUICK LOOKUP
        HashMap<LocalDate, ScheduleItem> holidayDates = new HashMap();
        for (ScheduleItem scheduleItem : scheduleItems) {
            holidayDates.put(scheduleItem.getDate(), scheduleItem);
        }           
        return holidayDates;
    }
        
    public HashMap<LocalDate, Lecture> getLectureMappings() {
        // FIRST LET'S PUT THE LECTURES IN A FUNNY CONSECUTIVE
        // ARRAY WHERE THEY OCCUR IN ORDER AND REPEAT ACCORDING
        // TO HOW MANY SESSIONS THEY HAVE
        ArrayList<Lecture> sessionsList = new ArrayList();
        for (Lecture lecture : lectures) {
            for (int i = 0; i < lecture.getSessions(); i++)
                sessionsList.add(lecture);
        }
        
        // GET THE SCHEDULE ITEM DATES FOR QUICK LOOKUP
        HashMap<LocalDate, ScheduleItem> holidayDates = getScheduleItemMappings();
        
        // NOW WE'LL MAP THOSE LECTURES TO DATES
        HashMap<LocalDate, Lecture> lectureMappings = new HashMap();
        LocalDate walkingDate = getStartingMonday();
        int totalSessions = sessionsList.size();
        int sessionsCounter = 0;
        while (!walkingDate.isAfter(endingFriday) && (sessionsCounter < totalSessions)) {
            if (hasLectureDay(walkingDate.getDayOfWeek()) && (!holidayDates.containsKey(walkingDate))) {
                lectureMappings.put(walkingDate, sessionsList.get(sessionsCounter));
                sessionsCounter++;
            }
            walkingDate = walkingDate.plusDays(1);
        }
        return lectureMappings;
    }
    
    public HashMap<LocalDate, Assignment> getAssignmentMappings() {
        HashMap<LocalDate, Assignment> assignmentMappings = new HashMap();
        for (Assignment assignment : assignments) {
            assignmentMappings.put(assignment.getDate(), assignment);
        }
        return assignmentMappings;
    }
    
    public Lecture getLectureForDate(LocalDate date) {
        //
        LocalDate walkingDate = getStartingMonday();
        int dayCounter = 0;
        int lectureCounter = 0;
        
        // FIRST FIGURE OUT THE LECTURE NUMBER
        while (!walkingDate.isAfter(date)) {
            // LET'S GO MONDAY THROUGH FRIDAY
            if ((getScheduleItemForDate(date) == null) 
                    && hasLectureDay(date.getDayOfWeek())){
                // THERE MAY BE A LECTURE ON THIS DAY
                lectureCounter++;                
            }
            dayCounter += 1;
            if (dayCounter == 5) {
                dayCounter += 3;
                walkingDate = walkingDate.plusDays(3);
            }
            else {
                dayCounter += 1;
                walkingDate = walkingDate.plusDays(1);
            }
        }
        
        if (lectureCounter == 0)
            return null;
        
        // lectureCounter NOW KNOWS HOW MANY LECTURE DATES THERE ARE
        // SO NOW GO THROUGH THE LECTURES AND COUNT
        int sessionCounter = 0;
        for (Lecture lecture : lectures) {
            if (sessionCounter >= lectureCounter)
                return lecture;
            else {
                sessionCounter += lecture.getSessions();
            }
        }
        
        return null;
    }
    
    public Assignment getAssignmentForDate(LocalDate date) {
        for (Assignment assignment : assignments) {
            if (assignment.getDate().equals(date))
                return assignment;
        }
        return null;
    }
}