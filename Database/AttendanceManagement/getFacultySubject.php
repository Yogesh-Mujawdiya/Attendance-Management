<?php
    require_once ('Config.php');
	$response = array();
	$SubjectList = array();
	$FID=$_POST['Id'];
	$Pass=$_POST['Password'];
	$Q=mysqli_query($con,"SELECT * FROM faculty WHERE Id='$FID' and Password='$Pass'");
	$num=mysqli_num_rows($Q);
	if($num==1){	
		$R=mysqli_query($con,"SELECT * FROM faculty_subject WHERE Faculty_Id='$FID'");
		$i=0;
		while ($I=mysqli_fetch_array($R)) {
			$SID = $I["Subject_Id"];
			$result=mysqli_query($con,"SELECT * FROM subject WHERE Id='$SID'");
			$num=mysqli_num_rows($result);
			if($num==1)
			{			
				$r = $result->fetch_assoc();
				$SubjectList[$i]["Name"] = $r["Name"] ;
				$SubjectList[$i]["Id"] = $SID;
				$i+=1;
			}
		}
		$response["status"] = "True";
		$response["message"] = "Data Fetch !";
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Invalid User!";
	}
	$response["SubjectList"] = $SubjectList;
	echo json_encode($response);
?>