begin
  foreach var n in EnumerateAllFiles(GetCurrentDir,'*layer_1*.png') do
  begin
    
  var name:=ExtractFileName(n).Replace('_layer_1.png','');
  var T:= ' {'+chr(13)+chr(10);
    T:=T+'"parent": "item/handheld",'+chr(13)+chr(10);
    T:=T+ '"textures": {'+chr(13)+chr(10);
    T:=T+ '  "layer0": "slaviccraft:items/'+name+'"'+chr(13)+chr(10);
    T:=T+ '   }'+chr(13)+chr(10);
    T:=T+ '}'+chr(13)+chr(10);
    WriteAllText(name+'.json',T);
  end;
end.