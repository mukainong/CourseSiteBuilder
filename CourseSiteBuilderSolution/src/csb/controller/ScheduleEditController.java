package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Assignment;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.data.Lecture;
import csb.data.ScheduleItem;
import csb.gui.AssignmentDialog;
import csb.gui.CSB_GUI;
import csb.gui.LectureDialog;
import csb.gui.MessageDialog;
import csb.gui.ScheduleItemDialog;
import csb.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class ScheduleEditController {
    ScheduleItemDialog sid;
    LectureDialog ld;
    AssignmentDialog ad;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public ScheduleEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new ScheduleItemDialog(initPrimaryStage, course, initMessageDialog);
        ld = new LectureDialog(initPrimaryStage);
        ad = new AssignmentDialog(initPrimaryStage, course, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddScheduleItemRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showAddScheduleItemDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            ScheduleItem si = sid.getScheduleItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addScheduleItem(si);

            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditScheduleItemRequest(CSB_GUI gui, ScheduleItem itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showEditScheduleItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            ScheduleItem si = sid.getScheduleItem();
            itemToEdit.setDescription(si.getDescription());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setLink(si.getLink());
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveScheduleItemRequest(CSB_GUI gui, ScheduleItem itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN REMOVE IT
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeScheduleItem(itemToRemove);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
    }
        
    public void handleAddLectureRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showAddLectureDialog();
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Lecture l = ld.getLecture();
            
            // AND ADD IT AS A ROW TO THE TABLE
            Lecture newLecture = new Lecture();
            newLecture.setTopic(l.getTopic());
            newLecture.setSessions(l.getSessions());
            course.addLecture(newLecture);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
        
    }
    
    public void handleRemoveLectureRequest(CSB_GUI gui, Lecture lectureToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN REMOVE IT
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeLecture(lectureToRemove);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
    }
    
    public void handleEditLectureRequest(CSB_GUI gui, Lecture lectureToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showEditLectureDialog(lectureToEdit);
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture l = ld.getLecture();
            lectureToEdit.setTopic(l.getTopic());
            lectureToEdit.setSessions(l.getSessions());
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }              
    }
    
    public void handleMoveUpLectureRequest(CSB_GUI gui, Lecture lectureToMoveUp) {
        gui.getDataManager().getCourse().moveUpLecture(lectureToMoveUp);
            
        // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
        // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
        // THE SAVE BUTTON IS ENABLED
        gui.getFileController().markAsEdited(gui);
    }
    
    public void handleMoveDownLectureRequest(CSB_GUI gui, Lecture lectureToMoveDown) {
        gui.getDataManager().getCourse().moveDownLecture(lectureToMoveDown);

        // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
        // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
        // THE SAVE BUTTON IS ENABLED
        gui.getFileController().markAsEdited(gui);
    }
    
    // THESE ARE FOR ASSIGNMENTS
    
    public void handleAddAssignmentRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showAddAssignmentDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // GET THE ASSIGNMENT
            Assignment a = ad.getAssignment();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addAssignment(a);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING  
        }
    }
    
    public void handleEditAssignmentRequest(CSB_GUI gui, Assignment assignmentToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showEditAssignmentDialog(assignmentToEdit);
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Assignment a = ad.getAssignment();
            assignmentToEdit.setName(a.getName());
            assignmentToEdit.setTopics(a.getTopics());
            assignmentToEdit.setDate(a.getDate());
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }              
    }
    
    public void handleRemoveAssignmentRequest(CSB_GUI gui, Assignment assignmentToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN REMOVE IT
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeAssignment(assignmentToRemove);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            gui.getFileController().markAsEdited(gui);
        }
    }
}