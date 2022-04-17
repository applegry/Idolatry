
begin
  Var STRINGS:=0;
  var CHARS:=0;
  var FILES:=0;
  var FS:array of string := ('*.java','*.json','*.pas','*.txt');
  foreach var b in Fs do
  foreach var s in EnumerateAllFiles(GetCurrentDir,b) do
  begin
    CHARS+=ReadAllText(s).Length;
        STRINGS+=ReadAllLines(s).Length;
    FILES+=1;
    
   // writeln(s);
  end;
  writeln('|------------------------------------------------|');
  writeln('|Всего строк: '+STRINGS+'                              ');
  writeln('|Всего символов: '+CHARS+'                            ');
  writeln('|Всего файлов: '+FILES+'                             ');
  writeln('|------------------------------------------------|');
  readln;
end.