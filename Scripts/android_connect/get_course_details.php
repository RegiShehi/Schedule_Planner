<?php

/*
 * Following code will get single course details
 * A course is identified by course id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];

    // get a course from courses table
    $result = mysql_query("SELECT *FROM courses WHERE pid = $pid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $course = array();
            $course["pid"] = $result["pid"];
            $course["name"] = $result["name"];
            $course["time"] = $result["time"];
			$course["room"] = $result["room"];
			$course["prof"] = $result["prof"];
            $course["description"] = $result["description"];
            $course["created_at"] = $result["created_at"];
            $course["updated_at"] = $result["updated_at"];
            // success
            $response["success"] = 1;

            // user node
            $response["course"] = array();

            array_push($response["course"], $course);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no course found
            $response["success"] = 0;
            $response["message"] = "No course found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no course found
        $response["success"] = 0;
        $response["message"] = "No course found";

        // echo no users JSON
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