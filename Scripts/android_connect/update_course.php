<?php

/*
 * Following code will update a course information
 * A course is identified by course id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['pid']) && isset($_POST['name']) && isset($_POST['time']) && isset($_POST['room']) && isset($_POST['prof']) && isset($_POST['description'])) {
    
    $pid = $_POST['pid'];
    $name = $_POST['name'];
    $time = $_POST['time'];
	$room = $_POST['room'];
	$prof = $_POST['prof'];
    $description = $_POST['description'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE courses SET name = '$name', time = '$time', room = '$room', prof = '$prof', description = '$description' WHERE pid = $pid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Course successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
