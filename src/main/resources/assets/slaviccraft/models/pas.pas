function getName(f:string):string;
begin
  Result:=ExtractFileName(f).Replace('cut_','').Replace('_block.json','');
end;

begin 
foreach var f in EnumerateAllFiles(GetCurrentDir(),'cut_*_block.json') do
  begin
    var t:= ReadAllText(f);
    var name := getName(f);
   // writeln(,name);
    t := t.Replace('_block','_idol');
    t := t.Replace('_log"','_idol"');
    t := t.Replace('cut_log/','idols/');
 //   writeln(t);
    WriteAllText(ExtractFileDir(f)+'\'+'cut_'+name+'_idol.json',t);
  end;
end.