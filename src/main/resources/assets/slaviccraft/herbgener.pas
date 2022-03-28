begin

  var herbs := 'hemlock betony nettle firewhip hellebore swiftfoot sleepgrass thornapples swordblade 0';
  foreach var name in herbs.Split(' ') do begin
  if(name = '0') then break;
WriteAllText('blockstates\'+name+'.json',('{\n  "variants": {\n    "half=lower": {\n      "model": "slaviccraft:block/'+name+'_bottom"\n    },\n    "half=upper": {\n      "model": "slaviccraft:block/'+name+'_top"\n    }\n  }\n}').Replace('\n',chr(13)+chr(10)));
writeln(' Blockstates was created: '+name);

WriteAllText('models\block\'+name+'_top.json',('{\n    "parent": "block/tinted_cross",\n    "textures": {\n        "cross": "slaviccraft:blocks/herbs/'+name+'_top"\n    }\n}').Replace('\n',chr(13)+chr(10)));
writeln('Block 1 model was created: '+name);

WriteAllText('models\block\'+name+'_bottom.json',('{\n    "parent": "block/tinted_cross",\n    "textures": {\n        "cross": "slaviccraft:blocks/herbs/'+name+'_bottom"\n    }\n}').Replace('\n',chr(13)+chr(10)));
writeln('Block 2 model was created: '+name);

WriteAllText('models\item\'+name+'.json',('{\n  "parent": "slaviccraft:block/'+name+'_top"\n}').Replace('\n',chr(13)+chr(10)));
writeln('Item model was created: '+name);
writeln('-----------------------------------------------------------------');
end;

end.

