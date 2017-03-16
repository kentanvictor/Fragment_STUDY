# Fragment_Study
## 注意：
新建的LeftFragment類需要繼承Fragment，值得注意的是，這裡會有提示導入兩個不同的Fragment包，其中一個是系統內置的android.app.fragment
一個是support-v4庫中的android.support.v4.app.Fragment。
### 這裡強烈建議使用support-v4庫中的Fragment，因為這樣才能使得碎片功能在所有的Android系統中保持一致性。
比如說，Fragment中嵌套使用Fragment,這個功能是在Android 4.2系統中才開始支持的，如果使用系統內置的Fragment
那麼在Android4.2系統之前的設備運行程序就會崩潰
詳情連接移步至

[LeftFragment.java](https://github.com/kentanvictor/Fragment_Study/blob/master/app/src/main/java/com/example/dell/fragment/LeftFragment.java)

### 動態添加碎片
詳情見
[AnotherRightFragment.java](https://github.com/kentanvictor/Fragment_Study/blob/master/app/src/main/java/com/example/dell/fragment/AnotherRightFragment.java)

#### 這個AnotherRightFragment的佈局文件的代碼與right_frament的佈局文件的代碼基本相同，只是將右側的碎片替換成了一個FrameLayout
#### 在MainActivity中的代碼，給左側碎片中的按鈕註冊了一個點擊事件，然後調用replaceFragment()方法動態添加了RightFragment這個碎片。當點擊左側碎片中的按鈕時，又會調用replaceFragment()方法將右側碎片替換成AnotherRightFragment。結合replaceFragment()方法中的代碼可以看出，動態添加碎片主要分為5步
* 1)创建待添加的碎片實例

* 2)獲取FragmentManager，在活動中可以直接通過調用getSupportFragmentManager()方法得到

* 3)開啟一個事務，通過調用commit()方法完成

* 4)向容器內添加或者替換碎片，一般使用replace()方法實現，需要傳入容器的id和待添加的碎片實例

* 5)提交事務，調用commit()方法來完成

### 在碎片中模擬返回棧
* 很簡單，在FragmentTransaction中提供了一個addToBackStack()方法，可以用於將一個事務添加到返回棧中，修改MainActivity中的代碼：
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener()
{
  ……
  private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
```
#### 這裡我們在事務提交之前調用了的FragmentTransaction的addToBackStack()方法，它可以接收一個名字用於描述返回棧的狀態，一般傳入null即可
### 在碎片和活動中進行通信
* 為了方便碎片和活動之間進行通信，FragmentManager提供了一個類似于findViewById()的方法，專門用於從佈局文件中獲取碎片的實例
```java
RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_layout);
```
#### 調用FragmentManager的findFragmentById()方法，可以在活動中得到相應碎片的實例，然後就能輕鬆地調用碎片裡面的方法了
