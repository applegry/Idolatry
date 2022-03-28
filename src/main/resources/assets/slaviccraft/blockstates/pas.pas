function getName(f:string):string;
begin
  Result:=ExtractFileName(f).Replace('cut_','').Replace('_block.json','');
end;

begin 
foreach var f in EnumerateAllFiles(GetCurrentDir(),'cut_*_block.json') do
  begin
  //  var t:= ReadAllText(f);
    var name := getName(f);

var s:=' {'+ chr(13)+chr(10)+
 ' "variants": {'+chr(13)+chr(10)+
 '   "facing=south":  { "model": "slaviccraft:block/cut_'+name+'_idol","y":180},'+chr(13)+chr(10)+
 '   "facing=north":  { "model": "slaviccraft:block/cut_'+name+'_idol","y":0},'+chr(13)+chr(10)+
 '   "facing=east":  { "model": "slaviccraft:block/cut_'+name+'_idol","y":90},'+chr(13)+chr(10)+
 '   "facing=west":  { "model": "slaviccraft:block/cut_'+name+'_idol","y":270}'+chr(13)+chr(10)+
'  }'+chr(13)+chr(10)+
'}';


    writeln(s);
    WriteAllText(ExtractFileDir(f)+'\'+'cut_'+name+'_idol.json',s);
  end;
end.