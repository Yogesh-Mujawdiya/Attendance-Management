<?php
    require_once ('Config.php');
	$response = array();
	$StudentList = array();
	$SID=$_POST['SubjectId'];
	$FID=$_POST['FacultyId'];
	$CID=$_POST['CourseId'];
	$DID=$_POST['DepartmentId'];
	$Section=$_POST['Section'];
	$QCode=$_POST['Qualification_Code'];
	$Batch=$_POST['Batch'];
	$Scount=$_POST['Student_Count'];
	$Batch = substr($Batch,-2);
	$R = $QCode.$DID.$CID.$Batch;
	if($Batch<19 && $DID='05' && $CID='1')
		$Scount=91;
	$i=0;
	while($i<=$Scount){
		$j=$i+1;
		$RollNo =  $R.sprintf("%'03d", $j);
		$StudentList[$i]["RollNo"] = $RollNo;
		$Q=mysqli_query($con,"SELECT count(*) as Present FROM attendance WHERE 
		Faculty_Id='$FID' and Subject_Name='$SID' and Roll_Number='$RollNo' and Status=1");
		$n=mysqli_num_rows($Q);
		if($n==1)
		{
			$J = $Q->fetch_assoc();
			$StudentList[$i]["Present"] = $J["Present"];
		}
		$Q=mysqli_query($con,"SELECT count(*) as Absent FROM attendance WHERE 
		Faculty_Id='$FID' and Subject_Name='$SID' and Roll_Number='$RollNo' and Status=0");
		$n=mysqli_num_rows($Q);
		if($n==1)
		{
			$J = $Q->fetch_assoc();
			$StudentList[$i]["Absent"] = $J["Absent"];
		}
		$i+=1;
	}
	$response["status"] = "True";
	$response["message"] = "Data Load !";
	$response["StudentList"] = $StudentList;
	echo json_encode($response);
?>