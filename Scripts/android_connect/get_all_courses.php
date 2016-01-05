<?php

/*
 * Following code will list all the courses
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all courses from courses table
$result = mysql_query("SELECT *FROM courses") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // courses node
    $response["courses"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $course = array();
        $course["pid"] = $row["pid"];
        $course["name"] = $row["name"];
        $course["time"] = $row["time"];
		$course["room"] = $row["room"];
		$course["prof"] = $row["prof"];
        $course["description"] = $row["description"];
        $course["created_at"] = $row["created_at"];
        $course["updated_at"] = $row["updated_at"];



        // push single course into final response array
        array_push($response["courses"], $course);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no courses found
    $response["success"] = 0;
    $response["message"] = "No courses found";

    // echo no users JSON
    echo json_encode($response);
}
?>
