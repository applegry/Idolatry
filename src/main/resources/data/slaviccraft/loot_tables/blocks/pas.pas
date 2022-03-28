begin
  var A:=('cranesbill lovage primrose honeyagaric betony cattails hellebore chanterelles artemisia nettle thistles thyme swordblade swiftfoot firewhip immortelle crysanths wildrosemary raveneye thornapples sleepgrass henbane hemlock').Split(' ');

  foreach var i in A do
  begin
      var S:=ReadAllText('firewhip.json');
      S:=S.Replace('firewhip',i);
   //   S:=S.Replace('minecraft:hellebore_bundle','slaviccraft:'+i+'_fresh');
      WriteAllText(i+'.json',S,Encoding.UTF8);
  end;
end.