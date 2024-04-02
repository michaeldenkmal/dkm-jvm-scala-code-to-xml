rem TODO
set code_file=D:\projekte\oh\oh_java_mv\wa_oh\src\main\scala\at\denkmal\libohws\ws\GpRes.scala
set xml_file=%code_file%.xml
set jar=.\target\scala-2.13\scalaCodeToJson-assembly-0.1.0-SNAPSHOT.jar
set main_class=at.denkmal.scala_code_to_xml.Main
java -cp %jar% %main_class% %code_file% %xml_file%
