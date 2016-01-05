<?php

/*
 * Following code will create a new course row
 * All course details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['name']) && isset($_POST['time']) && isset($_POST['time']) && isset($_POST['prof']) && isset($_POST['description'])) {
    
    $name = $_POST['name'];
    $time = $_POST['time'];
	$room = $_POST['room'];
	$prof = $_POST['prof'];
    $description = $_POST['description'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO courses(name, time, room, prof, description) VALUES('$name', '$time', '$room', '$prof', '$description')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Course successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>