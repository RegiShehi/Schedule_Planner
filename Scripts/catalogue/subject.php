<?php

/*
 * Simple JSON generation from static array
 */

include_once './data.php';

// check if course id is posted as GET param
if (isset($_GET["department"]) && $_GET["department"] != "" && isset($_GET["course"]) && $_GET["course"] != "") {
    $department_id = $_GET["department"];
    $course_id = $_GET["course"];
    // get the department
    $department = array_key_exists($department_id, $department_subjects) ? $department_subjects[$department_id] : NULL;
    if ($department != NULL) {
        // department found 
        // get the course
        $course = array_key_exists($course_id - 1, $department["courses"]) ? $department["courses"][$course_id - 1] : NULL;
        $course["department_id"] = $department_id;
        $course["department"] = $department["department"];
        echo json_encode($course);
    } else {
        // no department found
        echo "no department";
    }
}
?>
