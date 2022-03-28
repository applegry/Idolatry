begin 
  var c:=0;
  foreach var f in EnumerateAllFiles(GetCurrentDir(),'*.json') do begin
      var t:=ReadAllText(f);
      t:=t.Replace('tut','slaviccraft');
      WriteAllText(f,t);
      writeln(f);
      c+=1;
  end;
  writeln(c);
end.