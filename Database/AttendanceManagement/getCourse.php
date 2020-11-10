<?php
    require_once ('Config.php');
	$response = array();
	$SID=$_POST['SubjectId'];
	$CourseList = array();
	$Q2=mysqli_query($con,"SELECT * FROM course_subject WHERE Subject_Id='$SID'");
	$i=0;
	while ($I=mysqli_fetch_array($Q2)){
		$CID = $I["Course_Id"];
		$DID = $I["Department_Id"];
		$Q3=mysqli_query($con,"SELECT * FROM course WHERE Course_Id='$CID'");
		$n=mysqli_num_rows($Q3);
		if($n==1)
		{			
			$J = $Q3->fetch_assoc();
			$CourseList[$i]["Id"] = $CID ;
			$CourseList[$i]["DId"] = $DID;
			$CourseList[$i]["Name"] = $J["Course_Name"] ;
			$CourseList[$i]["Qualification_Code"] = $J["Qualification_Code"] ;
			$CourseList[$i]["Student_Count"] = $J["Student_Count"] ;
			$CourseList[$i]["Section_Count"] = 2 ;
			$i+=1;
		}
	}
	$response["status"] = "True";
	$response["message"] = "Data Fetch !";
	$response["CourseList"] = $CourseList;
	echo json_encode($response);
?>