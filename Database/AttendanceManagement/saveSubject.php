<?php
    require_once ('Config.php');
	$response = array();
	$SID=$_POST['SubjectId'];
	$CID=$_POST['CourseId'];
	$DID=$_POST['DepartmentId'];
	$FID=$_POST['Id'];
	$Pass=$_POST['Password'];
	$Q=mysqli_query($con,"SELECT * FROM faculty WHERE Id='$FID' and Password='$Pass'");
	$num=mysqli_num_rows($Q);
	if($num==1){	
		$reg="insert into faculty_subject values('$FID','$SID','$CID','$DID')";
		mysqli_query($con,$reg);
		$response["status"] = "True";
		$response["message"] = "Success !";
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Invalid User!";
	}
	echo json_encode($response);
?>