begin

  var herbs := 'lovage henbane primrose cranesbill raveneye 0';
  foreach var name in herbs.Split(' ') do begin
  if(name = '0') then break;
WriteAllText('blockstates\'+name+'.json',('{\n "variants": {\n "": {\n"model": "slaviccraft:block/'+name+'"\n  }\n }\n }\n').Replace('\n',chr(13)+chr(10)));
writeln(' Blockstates was created: '+name);

WriteAllText('models\block\'+name+'.json',('{\n    "parent": "block/tinted_cross",\n    "textures": {\n        "cross": "slaviccraft:blocks/subherbs/'+name+'"\n    }\n}').Replace('\n',chr(13)+chr(10)));
writeln('Block 1 model was created: '+name);

WriteAllText('models\item\'+name+'.json',('{\n  "parent": "slaviccraft:block/'+name+'"\n}').Replace('\n',chr(13)+chr(10)));
writeln('Item model was created: '+name);
writeln('-----------------------------------------------------------------');
end;

end.


