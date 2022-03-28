Uses GraphABC;
begin
  var p0:=new Picture('deeproot0.png');
  var p1:=new Picture('deeproot1.png');
  var p2:=new Picture('deeproot2.png');
  var p3:=new Picture('deeproot3.png');
  var p4:=new Picture('deeproot4.png');
  var p5:=new Picture('deeproot5.png');
  var p6:=new Picture('deeproot6.png');
  var p7:=new Picture('deeproot7.png');
  var out:=new Picture(16,16*8);
  out.CopyRect(Rect(0,0,16,16),p0,Rect(0,0,16,16));
  out.CopyRect(Rect(0,16,16,16*2),p1,Rect(0,0,16,16));
  out.CopyRect(Rect(0,32,16,16*3),p2,Rect(0,0,16,16));
  out.CopyRect(Rect(0,48,16,16*4),p3,Rect(0,0,16,16));
  out.CopyRect(Rect(0,64,16,16*5),p4,Rect(0,0,16,16));
  out.CopyRect(Rect(0,80,16,16*6),p5,Rect(0,0,16,16));
  out.CopyRect(Rect(0,96,16,16*7),p6,Rect(0,0,16,16));
  out.CopyRect(Rect(0,96+16,16,16*8),p7,Rect(0,0,16,16));
  out.Draw;
  out.Save('deeproot.png');
end.