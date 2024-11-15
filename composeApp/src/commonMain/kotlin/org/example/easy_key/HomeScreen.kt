package org.example.easy_key

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        // ...

        // ...底部导航栏
        val (currentScreen, setCurrentScreen) = remember { mutableStateOf(ScreenType.HOME_SCREEN) }
        Surface(modifier = Modifier.padding(top = 0.dp)) {
            Column(Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
                // 内容区域，根据currentScreen显示不同的页面
                ContentScreen(currentScreen)
            }
            Box(
                Modifier.fillMaxSize().border(1.dp, Color.Green),
                contentAlignment = Alignment.BottomEnd
            ) {
                BottomBar(currentScreen, setCurrentScreen)
            }
        }
    }

    private @Composable
    fun ContentScreen(currentScreen: ScreenType) {
        HomeContent()
        //val screenModel: PostScreenModel = getScreenModel()
//        when (currentScreen) {
//            ScreenType.HOME_SCREEN -> HomeContent()
//            ScreenType.CHAT_SCREEN -> SimpleBottomSheetScaffoldSample()
//            ScreenType.PUSH_SCREEN -> ""
//            ScreenType.SET_SCREEN -> ""
//        }
    }
}


@Composable
fun TopToolBar(currentTarget: Tools, setCurrentTarget: (Tools) -> Unit) {
    Box(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50))) {
        Surface(shape = RoundedCornerShape(20)) {
            Row(
                modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Tools.entries.forEach { tools ->
                    val isSelected = currentTarget == tools
                    TopToolBarItem(isSelected, tools.icon) { setCurrentTarget(tools) }
                }
            }
        }

    }
}

@Composable
fun TopToolBarItem(selected: Boolean, icon: ImageVector, onClick: () -> Unit) {

    val backgroundColor = if (selected) Color.Yellow else Color.Transparent
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.padding(end = 4.dp).clickable(
            interactionSource = interactionSource, indication = null, // 可以自定义点击反馈效果
            onClick = onClick
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(50))) {
            Surface(shape = RoundedCornerShape(20)) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.background(backgroundColor).padding(4.dp)
                )
            }
        }
    }

}

@Composable
fun BottomBar(currentScreen: ScreenType, setCurrentScreen: (ScreenType) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(65.dp)
            .padding(horizontal = 20.dp, vertical = 6.dp) // 外部padding
            //.shadow(elevation = 4.dp) // 在Box上添加阴影
            .clip(RoundedCornerShape(50)) // 使用Box裁剪形状
            .border(1.dp, Color.Yellow)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
                .background(androidx.compose.material3.MaterialTheme.colorScheme.surface) // 背景色
                .padding(horizontal = 20.dp, vertical = 6.dp), // 外部padding
            shape = RoundedCornerShape(50) // 设置圆角形状
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp), // 内部padding，防止按钮紧挨着边缘
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScreenType.entries.forEach { screenType ->
                    val isSelected = currentScreen == screenType
                    BottomBarItem(
                        selected = isSelected,
                        icon = if (isSelected) screenType.selectedIcon else screenType.unselectedIcon,
                        text = screenType.buttonName
                    ) { setCurrentScreen(screenType) }
                }
            }
        }
    }
}

@Composable
fun BottomBarItem(selected: Boolean, icon: ImageVector, text: String, onClick: () -> Unit) {

    val size by animateDpAsState(targetValue = if (selected) 22.dp else 18.dp) // 动画大小
    val iconColor by animateColorAsState(if (selected) androidx.compose.material3.MaterialTheme.colorScheme.primary else Color.Gray)
    val textColor by animateColorAsState(if (selected) androidx.compose.material3.MaterialTheme.colorScheme.primary else Color.Gray)
    // 定义基础的TextStyle，使用MaterialTheme.typography.body1作为起始点
    val baseTextStyle = androidx.compose.material3.MaterialTheme.typography.bodySmall

    // 根据selected的值来决定字体大小
    val textStyle = baseTextStyle.copy(
        fontSize = if (selected) 14.sp else 12.sp
    )
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.animateContentSize() // 添加动画效果
            .clickable(
                interactionSource = interactionSource, indication = null, // 可以自定义点击反馈效果
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(size) // 使用动画大小
        )
        Text(
            text = text, color = textColor, style = textStyle
        )
    }
}


@Composable
fun HomeContent() {
    Surface {
        Column(Modifier.fillMaxHeight()) {
            // ...标题栏（工具条）
            val (currentTarget, setTarget) = remember { mutableStateOf(Tools.TOOL_CALENDAR1) }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f).background(Color.LightGray)
            ) {
                Text("Title tools")
                Column(
                    Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopToolBar(currentTarget, setTarget)
                }
            }

            // ...内容栏（日期选择、日期显示、日历显示、状态标签）
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f).background(Color.White)
                    .padding(8.dp)
            ) {
                Column {
                    Text("Content calendar")
                    val instant = Clock.System.now()
                    val localDate =
                        remember { mutableStateOf(instant.toLocalDateTime(TimeZone.currentSystemDefault()).date) }
                    var currentYear by remember { mutableStateOf(localDate.value.year) }
                    var currentMonth by remember { mutableStateOf(localDate.value.month.number) }
                    var currentDay by remember { mutableStateOf(localDate.value.dayOfMonth) }
                    var selectedDay by remember { mutableStateOf<Int?>(currentDay) }
                    var showDialog by remember { mutableStateOf(true) }

                    Text(text = "${currentYear}—${currentMonth}")

                    val initialPage = 50
                    val oldPage = remember { mutableStateOf(initialPage) }
                    val pagerState =
                        rememberPagerState(pageCount = { 120 }, initialPage = oldPage.value)

                    Text(text = "pagerState.currentPage = ${pagerState.currentPage}")
                    // 监听滑动事件，并根据滑动方向更新年份和月份
                    LaunchedEffect(key1 = pagerState.currentPage) {
                        if (pagerState.currentPage == initialPage) {
                            currentYear = localDate.value.year
                            currentMonth = localDate.value.monthNumber
                        } else if (pagerState.currentPage > oldPage.value) { // 右滑
                            if (currentMonth == 12) {
                                currentMonth = 1
                                currentYear =
                                    if (currentYear == localDate.value.year) currentYear + 1 else currentYear
                            } else {
                                currentMonth += 1
                            }

                        } else if (pagerState.currentPage < oldPage.value) { // 左滑
                            if (currentMonth == 1) {
                                currentMonth = 12
                                currentYear =
                                    if (currentYear == localDate.value.year + 1) currentYear - 1 else currentYear
                            } else {
                                currentMonth -= 1
                            }
                        }
                        oldPage.value = pagerState.currentPage
                    }
                    // scroll to page
                    val coroutineScope = rememberCoroutineScope()

                    HorizontalPager(state = pagerState) { page ->
                        Column {
                            Text(text = "Calendar Month = ${page}")
                            CalendarView(currentYear, currentMonth, if (currentDay != 0) currentDay else null) { year, month, day ->
                                // 更新日期并关闭对话框
                                currentYear = year
                                currentMonth = month
                                currentDay = day
                                selectedDay = day // 更新选中的日期
                            }
                        }
                    }
                    if (currentYear == localDate.value.year && currentMonth == localDate.value.monthNumber) {
                    } else {
                        Button(onClick = {
                            coroutineScope.launch {
                                // Call scroll to on pagerState
                                pagerState.animateScrollToPage(initialPage)
                            }
                        }, modifier = Modifier) { Text("Back to today") }
                    }
                    if (showDialog) {
                        YearMonthGrid(year = currentYear,
                            month = currentMonth,
                            onYearMonthChange = { newYear, newMonth ->
                                currentYear = newYear
                                currentMonth = newMonth
                            },
                            onDialogDismiss = { showDialog = false })
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Content states set")
                        MoodStatusBar()
                    }
                }

            }
            // ...状态设置栏
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f).background(Color.Gray)) {
                Text("Content states set")
                // BottomSheetScaffold
                ActionBar()
            }
        }

    }

}

// ----------------------------------------------Calendar UI-----------------------------------------------------
@Composable
fun CalendarView(year: Int, month: Int, selectedDay: Int?, onDateSelected: (Int, Int, Int) -> Unit) {

    // Header with days of the week
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val daysInMonth = getDaysInMonth(year, month)
    val startDayOfWeek = getStartDayOfWeek(year, month)
    Row {
        Text(text = "daysInMonth = $daysInMonth, startDayOfWeek = $startDayOfWeek")
    }
    // Header with days of the week
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier.weight(1f).border(1.dp, Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day)
            }
        }

    }
    Spacer(modifier = Modifier.height(8.dp)) // Space between rows

    var fullMonthDays = 1 // 用于计算（上月末+本月+下月初）的计数,最大31 最小28
    var startDay = 1 // 用于计算当月要显示的天数，从1开始
    val rows =
        (daysInMonth + startDayOfWeek - 1) / 7 + if ((daysInMonth + startDayOfWeek - 1) % 7 > 0) 1 else 0

    // Days of the month
    for (row in 1..rows) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (col in 1..7) {
                if (fullMonthDays > daysInMonth) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        // Empty space
                    }

                } else if (startDayOfWeek > fullMonthDays) {
                    Box(
                        modifier = Modifier.weight(1f).border(1.dp, Color.Green),
                        contentAlignment = Alignment.Center
                    ) {

                    }
                } else Box(
                    modifier = Modifier.weight(1f).border(1.dp, Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    // Custom style!
                    // 1. circular shape display date
                    // 2. support selected date highlight
                    // 3. The date upper right corner have a state dot, it can display the status of the settings
                    // 4. The date background support provide a gradual change of color between different states.
                    //    that user can visually see state trends.
                    val isSelected = selectedDay == startDay
                    DayStyleBox(year, month, startDay,isSelected,onDateSelected)
                    startDay++
                }
                fullMonthDays++
            }
        }
        if (row < rows) {
            Spacer(modifier = Modifier.height(8.dp)) // Space between rows
        }
    }

}

fun getDaysInMonth(year: Int, month: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28
    else -> throw IllegalArgumentException("Invalid month")
}

fun getStartDayOfWeek(year: Int, month: Int): Int {
    val date = LocalDate(year, month, 1)
    return when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        DayOfWeek.SUNDAY -> 7
        else -> throw IllegalArgumentException("Invalid day of week")
    }
}

@Composable
fun DayStyleBox(
    year: Int,
    month: Int, // 需要传递年份参数
    day: Int, // 需要传递月份参数
    // status: 待定
    isSelected: Boolean,
    onDateSelected: (Int, Int, Int) -> Unit
) {
    // 获取当前日期的年、月、日
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val isToday = year == currentDate.year &&
            month == currentDate.monthNumber &&
            day == currentDate.dayOfMonth
    val isSelectedBackgroundColor = if (isSelected) Color.Yellow else Color.Transparent
    val isSelectedTextColor = if (isSelected) Color.White else Color.Black
    Box (modifier = Modifier.width(60.dp,).height(40.dp).padding(4.dp).clickable { onDateSelected(year, month, day) }) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                ) {
                    // Action Status
                }
            }
            Row(
                modifier = Modifier.width(30.dp,).height(25.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue).background(isSelectedBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (isToday) "今" else day.toString(), color = isSelectedTextColor)
                }
            }
        }
    }


}

// ------------------------------------------------Mood Status Bar--------------------------------------------------
@Composable
fun MoodStatus(emoji: String, text: String) {
    Column(
        modifier = Modifier.background(Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = emoji)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text)
        }
    }
}

@Composable
fun MoodStatusBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp).border(1.dp, Color.Yellow),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodStatus("😃", "Energy")
        MoodStatus("😢", "Break")
        MoodStatus("😡", "Emo")
        MoodStatus("😇", "Happiness")
    }
}

// -----------------------------------------------Dialog Select Year or Month---------------------------------------------------
@Composable
fun YearMonthGrid(
    year: Int, month: Int,
    onYearMonthChange: (Int, Int) -> Unit,
    onDialogDismiss: () -> Unit,
) {
    val years = remember { (2015..2035).toList() }
    val months = remember { (1..12).toList() }
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    Dialog(onDismissRequest = { onDialogDismiss() }) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                when (page) {
                    0 -> {
                        DateSelectGrid(months) { month ->
                            Box(
                                modifier = Modifier.size(60.dp) // 设置格子大小
                                    .padding(4.dp) // 设置格子间距
                                    .clickable {
                                        onYearMonthChange(year, month)
                                        onDialogDismiss()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = month.toString())
                            }
                        }
                    }

                    1 -> {
                        DateSelectGrid(years) { year ->
                            Box(
                                modifier = Modifier.size(60.dp) // 设置格子大小
                                    .padding(4.dp) // 设置格子间距
                                    .clickable {
                                        onYearMonthChange(year, month)
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(0)
                                        }

                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = year.toString())
                            }
                        }
                    }
                }
            }

            // 底部指示器
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until pagerState.pageCount) {
                    Surface(
                        modifier = Modifier.size(8.dp).padding(4.dp),
                        color = if (pagerState.currentPage == i) Color.Blue else Color.Gray
                    ) {}
                }
            }
        }
    }
}

@Composable
fun DateSelectGrid(list: List<Int>, content: @Composable (Int) -> Unit) {
    val rows = if (list.size % 3 == 0) list.size / 3 else list.size / 3 + 1
    val cols = 3

    Column(
        modifier = Modifier.background(Color.White).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(rows) { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    .border(1.dp, Color.Yellow), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(cols) { col ->
                    val index = row * cols + col
                    if (index < list.size) {
                        Column(
                            modifier = Modifier.border(1.dp, Color.Green),
                            verticalArrangement = Arrangement.Center
                        ) { content(list[index]) }
                    }

                }
            }
        }
    }

}

// -----------------------------------------------Action Bar---------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBar() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberFlexibleBottomSheetState(
        skipHiddenState = true,
        flexibleSheetSize = FlexibleSheetSize(fullyExpanded = 0.2f),
        isModal = true,
        skipSlightlyExpanded = false,
    )

    FlexibleBottomSheet(
        sheetState = sheetState,
        containerColor = Color.Black,
        onDismissRequest = { }
    ) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                scope.launch {
                    when (sheetState.swipeableState.currentValue) {
                        FlexibleSheetValue.SlightlyExpanded -> sheetState.intermediatelyExpand()
                        FlexibleSheetValue.IntermediatelyExpanded -> sheetState.fullyExpand()
                        else -> sheetState.hide()
                    }
                }
            },
        ) {
            Text(text = "Expand Or Hide")
        }
    }
}

@Composable
fun ActionStatus() {
    Row(modifier = Modifier.fillMaxWidth()) {

    }
}
