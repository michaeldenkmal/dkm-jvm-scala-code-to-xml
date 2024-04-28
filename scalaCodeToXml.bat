rem TODO
set code_file=%1
set xml_file=%2
set jar=.\target\scala-2.13\scalaCodeToJson-assembly-0.1.0-SNAPSHOT.jar
set main_class=at.denkmal.scala_code_to_xml.Main
cd /D %~dp0
java -cp %jar% %main_class% %code_file% %xml_file%
