<?php
    require_once ('Config.php');
	$response = array();
	$SID=$_POST['SubjectId'];
	$FID=$_POST['FacultyId'];
	$Password=$_POST['Password'];
	$PresentRollNo= explode(',',$_POST['PresentRollNo']);
	$AbsentRollNo = explode(',',$_POST['AbsentRollNo']);
	$Q=mysqli_query($con,"SELECT * FROM Faculty WHERE Id='$FID' and Password='$Password'");
	$n=mysqli_num_rows($Q);
	if($n==1)
	{
		$i=0;
		while($i<count($PresentRollNo)){
			$P="INSERT INTO attendance(Faculty_Id, Subject_Name, Roll_Number, Status) 
			values('$FID','$SID','$PresentRollNo[$i]','1');";
			mysqli_query($con,$P);
			$i+=1;
		}
		$i=0;
		while($i<count($AbsentRollNo)){
			$P="INSERT INTO attendance(Faculty_Id, Subject_Name, Roll_Number, Status)
			 VALUES('$FID','$SID','$AbsentRollNo[$i]','0')";
			mysqli_query($con,$P);
			$i+=1;
		}
		$response["status"] = "True";
		$response["message"] = "Data Saved !";
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Invalid User !";
	}
	echo json_encode($response);
?>