<?php
    require_once ('Config.php');
	$response = array();
	$SId=$_POST['SubjectId'];
	$FID=$_POST['Id'];
	$Pass=$_POST['Password'];
	$CourseList = array();
	$Q=mysqli_query($con,"SELECT * FROM faculty WHERE Id='$FID' and Password='$Pass'");
	$num=mysqli_num_rows($Q);
	if($num==1){
		$Q=mysqli_query($con,"SELECT * FROM faculty_subject WHERE Subject_Id='$SId' and Faculty_Id='$FID'");
		$i=0;
		while ($I=mysqli_fetch_array($Q)){
			$CID = $I["Course_Id"];
			$DID = $I["Department_Id"];
			$S=mysqli_query($con,"SELECT * FROM course WHERE Course_Id='$CID' and Department_Id='$DID'");
			$n=mysqli_num_rows($S);
			if($n==1)
			{			
				$J = $S->fetch_assoc();
				$CourseList[$i]["Id"] = $J["Course_Id"] ;
				$CourseList[$i]["Department_Id"] = $J["Department_Id"] ;
				$CourseList[$i]["Name"] = $J["Course_Name"] ;
				$i+=1;
			}
		}
		$response["status"] = "True";
		$response["message"] = "Success !";
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Invalid User!";
	}
	$response["CourseList"] = $CourseList;
	echo json_encode($response);
?>