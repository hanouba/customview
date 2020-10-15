1
PATH
moveto是移动到某个坐标，lineto是从当前坐标移动的某个坐标连接早当前坐标
1.2  Path--->quadTo(float x1, float y1, float x2, float y2):
 该方法的实现是当我们不仅仅是画一条线甚至是画弧线时会形成平滑的曲线，
 该曲线又称为"贝塞尔曲线"(Bezier curve)，其中，x1，y1为控制点的坐标值，
 x2，y2为终点的坐标值