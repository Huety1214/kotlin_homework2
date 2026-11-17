# Kotlin Homework2 報告（Lab4 點餐系統、Lab5 生命週期觀測、Lab6 訊息提示與互動）

> 繳交檔名：`113360122_林彥廷.pdf`

## 一、讀書會
| 項目 | 內容 |
|---|---|
| 組員 | （填寫） |
| 討論時間 | （填寫） |
| 地點 / Google Meet | （填寫） |
| 照片 / 截圖 | （貼上） |

## 二、GitHub
- 程式連結：`https://github.com/Huety1214/kotlin_homework2`
- GitHub 截圖：（貼上）

## 三、Lab 內容
- **Lab4 點餐系統**：MainActivity 用 `registerForActivityResult` 開啟 SecActivity，使用者選好飲料、甜度、冰塊後用 `setResult` 回傳，MainActivity 顯示結果。
- **Lab5 生命週期觀測**：ViewPager2 搭配三個 Fragment，在每個生命週期方法印出 Log，觀察滑動頁面、切到背景時 Activity 與 Fragment 的呼叫順序。
- **Lab6 訊息提示**：Toast、Snackbar（含按鈕）、三種 AlertDialog（按鈕式、列表式、單選式）。

## 四、優化程式碼心得

**Lab4**
- Intent 的 key（`"drink"`、`"sugar"`、`"ice"`）改成 `SecActivity` 的 `companion object` 常數，兩邊共用，避免打錯字造成拿到 null。
- 寫一個擴充函式 `RadioGroup.checkedText()`，取代兩次重複的 `findViewById<RadioButton>(checkedRadioButtonId).text.toString()`。
- 回傳值用 `getStringExtra(...) ?: "無"` 處理 null，不會在畫面上顯示 "null"。

**Lab5**
- 原本三個 Fragment 各自覆寫 11 個生命週期方法，內容幾乎一樣（約 200 行重複）。抽出 `LifecycleFragment` 父類別，Log 的 TAG 自動使用類別名稱，子類別只剩一行：
  ```kotlin
  class FirstFragment : LifecycleFragment(R.layout.fragment_first)
  ```
- `ViewPagerAdapter` 的頁面改成 `listOf(::FirstFragment, ::SecondFragment, ::ThirdFragment)`，`getItemCount()` 直接回傳 `pages.size`，新增頁面只要改一個地方。

**Lab6**
- 沒用到的 lambda 參數 `dialogInterface, which` 改成 `_, _`，一眼看出哪些參數有用。
- 選項陣列用 `Array(5) { "選項 ${it + 1}" }` 產生。
- 共用的 `AlertDialog.Builder(this).setTitle(...)` 抽成 `dialog(title)`，按鈕綁定抽成 `onClick(id) { }`。

## 五、Java 與 Kotlin 語法差異

**1. 傳遞資料（Bundle / Intent）**

Java 語法
```java
Bundle b = new Bundle();
b.putString("drink", edDrink.getText().toString());
Intent i = new Intent();
i.putExtras(b);
setResult(Activity.RESULT_OK, i);
```
Kotlin 語法
```kotlin
val result = Intent().apply {          // apply：在同一區塊設定物件
    putExtra(KEY_DRINK, drink)
}
setResult(RESULT_OK, result)
```

**2. 常數**

Java 語法
```java
public static final String KEY_DRINK = "drink";
```
Kotlin 語法
```kotlin
companion object { const val KEY_DRINK = "drink" }
```

**3. Null 處理**

Java 語法
```java
String drink = intent != null ? intent.getStringExtra("drink") : null;
if (drink == null) drink = "無";
```
Kotlin 語法
```kotlin
val drink = result.data?.getStringExtra(KEY_DRINK) ?: "無"   // 安全呼叫 + Elvis
```

**4. 繼承與建構子**

Java 語法
```java
public class FirstFragment extends LifecycleFragment {
    public FirstFragment() { super(R.layout.fragment_first); }
}
```
Kotlin 語法
```kotlin
class FirstFragment : LifecycleFragment(R.layout.fragment_first)
```

**5. 匿名類別 vs lambda（AlertDialog）**

Java 語法
```java
.setPositiveButton("右按鈕", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) { showToast("右按鈕"); }
})
```
Kotlin 語法
```kotlin
.setPositiveButton("右按鈕") { _, _ -> showToast("右按鈕") }
```

**6. 擴充函式（Java 沒有）**

Java 語法
```java
static String checkedText(RadioGroup rg) {
    return ((RadioButton) rg.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
}
checkedText(rgSugar);
```
Kotlin 語法
```kotlin
fun RadioGroup.checkedText() = findViewById<RadioButton>(checkedRadioButtonId).text.toString()
rgSugar.checkedText()
```

## 六、Lab5 生命週期觀察結果（請貼上自己 Logcat 截圖）
- 開啟 App：MainActivity onCreate → onStart → onResume；FirstFragment、SecondFragment（offscreenPageLimit = 1 預先載入）onAttach → onCreate → onCreateView → onViewCreated → onStart，目前頁面才會 onResume。
- 滑到第二頁：FirstFragment onPause，SecondFragment onResume，ThirdFragment 開始建立。
- 按 Home：目前頁面 onPause → onStop，MainActivity onPause → onStop；回來時 onRestart → onStart → onResume。

## 七、心得
（依自己的感想修改）這次發現 Activity 之間傳值很容易因為 key 打錯而拿到 null，改成常數後安全很多。Lab5 把重複的生命週期程式碼抽成父類別，程式從三個長檔案變成一個父類別加三行，也更清楚看到繼承的好處。
