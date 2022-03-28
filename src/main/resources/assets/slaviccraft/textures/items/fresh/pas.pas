begin

  var A:=('crysanth firewhip hellebore hemlock henbane immortelle raveneye sleepgrass swiftfoot swordblade thornapples wildrosemary').Split(' ');

  foreach var i in A do
  begin
      var S:=ReadAllText('hellebore_fresh.json');
      S:=S.Replace('items/fresh/hellebore','items/fresh/'+i);
     S:=S.Replace('hellebore_dried',i+'_fresh');
      WriteAllText(i+'_fresh.json',S,Encoding.UTF8);
  end;
end.