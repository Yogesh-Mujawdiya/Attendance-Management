<?php
    require_once ('Config.php');
	$response = array();
	$SID=$_POST['SubjectId'];
	$CID=$_POST['CourseId'];
	$DID=$_POST['DepartmentId'];
	$Batch = $_POST['Batch'];
	$StudentList = array();
	$Q=mysqli_query($con,"SELECT * FROM course WHERE Department_Id='$DID' and Course_Id='$CID'");
	$n=mysqli_num_rows($Q);
	if($n==1)
	{
		$D = $Q->fetch_assoc();
		$QCode = $D['Qualification_Code'];
		$Scount = $D['Student_Count'];
		$Q=mysqli_query($con,"SELECT * FROM course_subject WHERE Subject_Id='$SID' and Course_Id='$CID'");
		$n=mysqli_num_rows($Q);
		if($n==1)
		{
			$D = $Q->fetch_assoc();
			$Sem = $D['Semester'];
			$Batch = substr($Batch,-2);
			$R = $QCode.$DID.$CID.$Batch;
			if($Batch<19 && $DID='05' && $CID='1')
				$Scount=91;
			$i=0;
			while($i<=$Scount){
				$j=$i+1;
				$StudentList[$i] = $R.sprintf("%'03d", $j);
				$i+=1;
			}
		}
	}
	$response["status"] = "True";
	$response["message"] = "Data Fetch !";
	$response["SList"] = $StudentList;
	echo json_encode($response);
?>