# Fragment_Study
## 注意：
新建的LeftFragment類需要繼承Fragment，值得注意的是，這裡會有提示導入兩個不同的Fragment包，其中一個是系統內置的android.app.fragment
一個是support-v4庫中的android.support.v4.app.Fragment。
###這裡強烈建議使用support-v4庫中的Fragment，因為這樣才能使得碎片功能在所有的Android系統中保持一致性。
比如說，Fragment中嵌套使用Fragment,這個功能是在Android 4.2系統中才開始支持的，如果使用系統內置的Fragment
那麼在Android4.2系統之前的設備運行程序就會崩潰
詳情連接移步至

[LeftFragment.java](https://github.com/kentanvictor/Fragment_Study/blob/master/app/src/main/java/com/example/dell/fragment/LeftFragment.java)
