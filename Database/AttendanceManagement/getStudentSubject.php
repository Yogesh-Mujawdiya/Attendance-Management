<?php
    require_once ('Config.php');
	$response = array();
	$SubjectList = array();
	$ID=$_POST['Id'];
	$QCode = substr($ID, 0, 1);
	$DID = substr($ID, 1, 2);
	$CID = substr($ID, 3, 1);
	$year = date("Y");
	$Sem = "5";

	$Q =  "SELECT Subject_Id FROM course_subject where
	Course_Id='$CID' and Department_Id='$DID' and
	Qualification_Code = '$QCode' and Semester = '$Sem';";
	$R=mysqli_query($con,$Q);
	$i=0;
	while ($I=mysqli_fetch_array($R)) {
		$SID = $I["Subject_Id"];
		$SubjectList[$i]["Id"] = $SID;
		$Q=mysqli_query($con,"SELECT * FROM subject WHERE Id='$SID';");
		$n=mysqli_num_rows($Q);
		if($n==1)
		{
			$J = $Q->fetch_assoc();
			$SubjectList[$i]["Name"] = $J["Name"];
		}
		$Q=mysqli_query($con,"SELECT count(*) as Attend FROM attendance WHERE Subject_Name='$SID' and Roll_Number='$ID' and Status=1;");
		$n=mysqli_num_rows($Q);
		$SubjectList[$i]["Attend"] = '0';
		if($n==1)
		{
			$J = $Q->fetch_assoc();
			$SubjectList[$i]["Attend"] = $J["Attend"];
		}
		$Q=mysqli_query($con,"SELECT count(*) as Total FROM attendance WHERE Subject_Name='$SID' and Roll_Number='$ID';");
		$n=mysqli_num_rows($Q);
		$SubjectList[$i]["Total"] = '0';
		if($n==1)
		{
			$J = $Q->fetch_assoc();
			$SubjectList[$i]["Total"] = $J["Total"];
		}
		$i+=1;
	}
	$response["SubjectList"] = $SubjectList;
	$response["status"] = "True";
	$response["message"] = "Data Fetch !";
	echo json_encode($response);
?>