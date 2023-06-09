# Bomberman - Bài tập lớn OOP UET

## Thành viên nhóm 07

| STT | Họ và tên            | Mã sinh viên | Ngày sinh  |
| --- | -------------------- | ------------ | ---------- |
| 1   | Nguyễn Quang Thao    | 22022619     | 19/07/2004 |
| 2   | Nguyễn Trần Hải Ninh | 22022526     | 23/11/2004 |


## Mô tả về các đối tượng trong trò chơi

Nếu bạn đã từng chơi Bomberman, bạn sẽ cảm thấy quen thuộc với những đối tượng này. Chúng được được chia làm hai loại chính là nhóm đối tượng động (_Bomber_, _Enemy_, _Bomb_) và nhóm đối tượng tĩnh (_Grass_, _Wall_, _Brick_, _Door_, _Item_,...).

_Các đối tượng được xây dựng:_

- ![](res/sprites/player_down.png) _Bomber_ là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- ![](res/sprites/balloom_left1.png) _Enemy_ là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](res/sprites/bomb.png) _Bomb_ là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng _Flame_ ![](res/sprites/explosion_horizontal.png) được tạo ra.

- ![](res/sprites/grass.png) _Grass_ là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](res/sprites/wall.png) _Wall_ là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
- ![](res/sprites/brick.png) _Brick_ là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.

- ![](res/sprites/portal.png) _Portal_ là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

Các _Item_ cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:

- ![](res/sprites/powerup_speed.png) _SpeedItem_ Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
- ![](res/sprites/powerup_flames.png) _FlameItem_ Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
- ![](res/sprites/powerup_bombs.png) _BombItem_ Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, _Bomber_ sẽ được đặt và kích hoạt duy nhất một đối tượng _Bomb_. Item này giúp tăng số lượng Bomb có thể đặt thêm một.
- ![](res/sprites/powerup_detonator.png) _Detonator_ Khi sử dụng Item này bạn có thể kích hoạt Bomb nổ sớm hơn dự kiến.
- ![](res/sprites/powerup_flamepass.png) _FlamePass_ Khi sử dụng Item này, bomb sẽ có tác động kể cả khi có vật cản (có tác dụng khi _FlameItem_ được kích hoạt).
- ![](res/sprites/powerup_wallpass.png) _WallPass_ Khi Item được kích hoạt thì Bomber sẽ có khả năng đi qua _Wall_ hoặc _Brick_.

Có nhiều loại Enemy trong Bomberman, tuy nhiên trong phiên bản này chỉ cài đặt những loại Enemy dưới đây:

- ![](res/sprites/balloom_left1.png) _Balloom_ là Enemy đơn giản nhất, di chuyển ngẫu nhiên với vận tốc cố định
- ![](res/sprites/oneal_left1.png) _Oneal_ có khả năng dò đường và di chuyển tới Bomber (sử dụng thuật toán BFS - kết quả tương đương thuật toán A*)
- ![](res/sprites/doll_left1.png) _Doll_ có khả năng di chuyển xuyên qua vật thể nhưng tốc độ chậm hơn những Enemy khác.
- ![](res/sprites/kondoria_left1.png) _Kondoria_ khi chết sẽ sinh ra 2 _Balloom_.

### Diagram
<img src="./diagrams_img.png" height="100%">

## Mô tả game play, xử lý va chạm và xử lý bom nổ

- Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy và tìm ra vị trí Portal để có thể qua màn mới
- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ. Lúc đấy trò chơi kết thúc.
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.

- Khi Bomb nổ, một Flame trung tâm![](res/sprites/bomb_exploded.png) tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên ![](res/sprites/explosion_vertical.png) | dưới ![](res/sprites/explosion_vertical.png) | trái ![](res/sprites/explosion_horizontal.png) | phải ![](res/sprites/explosion_horizontal.png). Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Brick/Wall nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Brick/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Brick/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng. Còn nếu vật cản Flame là một đối tượng Bomb khác thì đối tượng Bomb đó cũng sẽ nổ ngay lập tức.

## Các phím trong game

- A/S/D/W: di chuyển _Bomber_ lần lượt là _trái/dưới/phải/trên_
- H/Space: Đặt bom
- Q/ESC: Tạm dừng game
- J/F: Kích hoạt _Bomb_