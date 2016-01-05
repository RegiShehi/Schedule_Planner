<?php

/*
 * Simple JSON generation from static array
 */

include_once './data.php';
$departments = array();

// looping through each department
foreach ($department_subjects as $department) {
    $tmp = array();
    $tmp["id"] = $department["id"];
    $tmp["name"] = $department["department"];
    $tmp["courses_count"] = count($department["courses"]);

    // push department
    array_push($departments, $tmp);
}

// printing json
echo json_encode($departments);
?>
