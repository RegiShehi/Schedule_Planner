<?php
/*
 * Simple JSON generation from static array
 */

include_once './data.php';

// Check if department id is posted as GET parameter
if (isset($_GET["id"]) && $_GET["id"] != "") {

    // Check if the department id exists in department_subjects array
    if (array_key_exists($_GET["id"], $department_subjects)) {
        // print department subjects json
        echo json_encode($department_subjects[$_GET["id"]]);
    } else {
        // no department found
        echo "{}";
    }
}
?>
