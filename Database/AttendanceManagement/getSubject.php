<?php
    require_once ('Config.php');
	$response = array();
	$SubjectList = array();
	$R=mysqli_query($con,"SELECT * FROM subject");
	$i=0;
	while ($I=mysqli_fetch_array($R)) {
		$SubjectList[$i]["Name"] = $I["Name"] ;
		$SubjectList[$i]["Id"] = $I["Id"] ;
		$i+=1;
	}
	$response["SubjectList"] = $SubjectList;
	$response["status"] = "True";
	$response["message"] = "Data Fetch !";
	echo json_encode($response);
?>