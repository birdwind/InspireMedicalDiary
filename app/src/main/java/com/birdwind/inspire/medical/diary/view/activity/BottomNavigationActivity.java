package com.birdwind.inspire.medical.diary.view.activity;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.network.WebSocketClient;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentHistory;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentNavigationListener;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityBottomNavigationBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.service.InspireDiaryChatService;
import com.birdwind.inspire.medical.diary.view.customer.BadgeView;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leaf.library.StatusBarUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import q.rorbin.badgeview.Badge;

public class BottomNavigationActivity extends AbstractActivity<AbstractPresenter, ActivityBottomNavigationBinding>
    implements FragmentNavigationListener, FragNavController.RootFragmentListener,
    FragNavController.TransactionListener, AbstractActivity.PermissionRequestListener,
    BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final int NOTIFICATION = R.id.menu_notification;

    private final int SCHEDULE = R.id.menu_schedule;

    private final int FORM = R.id.menu_form;

    private final int SIGN = R.id.menu_sign;

    private final int ME = R.id.menu_me;

    private boolean doubleBackToExitPressedOnce = false;

    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    private FragNavTransactionOptions tabToRightFragNavTransactionOptions;

    private FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    private int currentNavigationPosition;

    private int targetSelectedPosition;

    private int popIndexTab;

    public Badge[] badge;

    private ToolbarCallback toolbarCallback;

    @Override
    public ActivityBottomNavigationBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityBottomNavigationBinding.inflate(getLayoutInflater());
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new ScanFragment();
            case FragNavController.TAB2:
                return new ScanFragment();
            case FragNavController.TAB3:
                return new ScanFragment();
            case FragNavController.TAB4:
                return new ScanFragment();
            case FragNavController.TAB5:
                return new ScanFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (popIndexTab != -1 && popIndexTab != index) {
            if (!mNavController.isRootFragment(popIndexTab)) {
                mNavController.popIndexTabFragment(popIndexTab);
            }
            popIndexTab = -1;
        }
        LogUtils.e("TabTransaction");
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        LogUtils.e("FragmentTransaction");

    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void popIndexTabFragment(int tab) {
        popIndexTab = tab;
    }

    @Override
    public void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode,
        boolean isShowBack, boolean isShowHeader, boolean isShowRightButton, String rightButtonText,
        @DrawableRes int rightImageButton, ToolbarCallback toolbarCallback, String leftButtonText) {
        if (title != null) {
            binding.compTopBarBottomNavigationActivity.tvTitleTopBarComp.setText(title);
        }
        if (isStatusLightMode) {
            StatusBarUtil.setLightMode(this);
        } else {
            StatusBarUtil.setDarkMode(this);
        }
        binding.compTopBarBottomNavigationActivity.tvTitleTopBarComp
            .setTextColor(ContextCompat.getColor(this, titleColor));
        binding.compTopBarBottomNavigationActivity.ivBackTopBarComp
            .setColorFilter(ContextCompat.getColor(this, titleColor), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.compTopBarBottomNavigationActivity.rlBackgroundTopBarComp
            .setBackgroundColor(ContextCompat.getColor(this, backgroundColor));

        if (isShowBack) {
            binding.compTopBarBottomNavigationActivity.llBackTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarBottomNavigationActivity.llBackTopBarComp.setVisibility(View.INVISIBLE);
        }

        if (isShowHeader) {
            binding.compTopBarBottomNavigationActivity.rlBackgroundTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarBottomNavigationActivity.rlBackgroundTopBarComp.setVisibility(View.GONE);
        }

        if (isShowRightButton) {
            binding.compTopBarBottomNavigationActivity.btRightButtonTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarBottomNavigationActivity.btRightButtonTopBarComp.setVisibility(View.GONE);
        }

        binding.compTopBarBottomNavigationActivity.btRightButtonTopBarComp.setText(rightButtonText);

        if (rightImageButton != 0) {
            binding.compTopBarBottomNavigationActivity.llRightButtonTopBarComp.setVisibility(View.VISIBLE);
            binding.compTopBarBottomNavigationActivity.ivRightButtonTopBarComp
                .setImageDrawable(ContextCompat.getDrawable(context, rightImageButton));
        } else {
            binding.compTopBarBottomNavigationActivity.llRightButtonTopBarComp.setVisibility(View.GONE);
        }

        this.toolbarCallback = toolbarCallback;

    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public void addListener() {
        binding.bottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        binding.compTopBarBottomNavigationActivity.llBackTopBarComp.setOnClickListener(this);
        binding.compTopBarBottomNavigationActivity.btRightButtonTopBarComp.setOnClickListener(this);
        binding.compTopBarBottomNavigationActivity.llRightButtonTopBarComp.setOnClickListener(this);
        binding.compTopBarBottomNavigationActivity.btLeftButtonTopBarComp.setOnClickListener(this);
    }

    @Override
    public void initView() {
        StatusBarUtil.setLightMode(this);
        binding.bottomNavigationViewEx.setItemHeight(Utils.dp2px(this, 82));
        binding.bottomNavigationViewEx.setIconsMarginTop(Utils.dp2px(this, 13));
        // binding.bottomNavigationViewEx.setItemBackground(
        // ResourcesCompat.getDrawable(getResources(), R.drawable.selector_color_navigation_icon, null));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        currentNavigationPosition = 0;
        popIndexTab = -1;
        badge = new Badge[5];

        FragNavTransactionOptions defaultFragNavTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();

        tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        fragmentHistory = new FragmentHistory();

        mNavController =
            FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), binding.mainContainer.getId())
                .transactionListener(this).rootFragmentListener(this, 5)
                .defaultTransactionOptions(defaultFragNavTransactionOptions).build();
    }

    @Override
    public void doSomething() {
        if (targetSelectedPosition != 0) {
            onNavigationItemClicked(targetSelectedPosition);
        }
        // createSignalR();
        // createWebSocket();
    }

    private void startSignalRService() {
        if (!SystemUtils.isServiceRunning(InspireDiaryChatService.class, context)) {
            Intent intent = new Intent(this, InspireDiaryChatService.class);
            this.startService(intent);
        }
    }

    private void createWebSocket() {
        WebSocketClient webSocketClient = new WebSocketClient(Config.BASE_URL + "chatHub");
        webSocketClient.start(new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                LogUtils.d("WebSocket", "onOpen()");
            }

            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                LogUtils.d("WebSocket", "onClosed()");
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                LogUtils.d("WebSocket", "onClosing()");
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                LogUtils.d("WebSocket", "onFailure()");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                LogUtils.d("WebSocket", "onMessage()");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                LogUtils.d("WebSocket", "onMessage()");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
                break;
            case NOTIFICATION:
                onNavigationItemClicked(0);
                break;
            case SCHEDULE:
                onNavigationItemClicked(1);
                break;
            case FORM:
                onNavigationItemClicked(2);
                break;
            case SIGN:
                onNavigationItemClicked(3);
                break;
            case ME:
                onNavigationItemClicked(4);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
        } else {
            if (fragmentHistory.isEmpty()) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                showToast(getString(R.string.common_double_click_back));
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            } else {
                if (fragmentHistory.getStackSize() > 1) {
                    int position = fragmentHistory.popAndPeekPrevious();
                    switchTab(position);

                } else {
                    switchTab(0);
                    fragmentHistory.emptyStack();
                }
            }
        }
    }

    public void onPopBack(int popTabIndex, int popDeep) {
        mNavController.popIndexTabFragment(popTabIndex, popDeep, tabToLeftFragNavTransactionOptions);
    }

    public void onNavigationItemClicked(int tab) {
        if (currentNavigationPosition == tab && !mNavController.isRootFragment()) {
            mNavController.clearStack(popFragNavTransactionOptions);
        }
        switchTab(tab);
        fragmentHistory.push(tab);
    }

    private void switchTab(int position) {
        binding.bottomNavigationViewEx.moveTabIndicator(position, true);
        // if (position == 1) {
        // if (currentNavigationPosition != 0) {
        // onNavigationItemClicked(0);
        // }
        // homeFragment.switchTab(4);
        // resetMenuItem(1);
        //
        // } else {
        if (position > currentNavigationPosition) {
            mNavController.switchTab(position, tabToRightFragNavTransactionOptions);
        } else if (position < currentNavigationPosition) {
            mNavController.switchTab(position, tabToLeftFragNavTransactionOptions);
        }
        resetMenuItem(position);
        currentNavigationPosition = position;
        // }
        //
        // if (position == 0) {
        // homeFragment.switchTab(0);
        // }
    }

    public void resetMenuItem(int selectorItemIndex) {
        Menu menu = binding.bottomNavigationViewEx.getMenu();
        for (int i = 0; i < 5; i++) {
            menu.getItem(i).setChecked(false);
        }
        menu.getItem(selectorItemIndex).setChecked(true);
    }

    private Badge setBadge(int position, int unreadCount) {
        return new BadgeView(this).setBadgeNumber(unreadCount).setGravityOffset(12, 2, true)
            .bindTarget(binding.bottomNavigationViewEx.getBottomNavigationItemView(position));
    }

    public void addBadge(int position, int unreadCount) {
        if (unreadCount > 0) {
            if (badge[position] == null) {
                badge[position] = setBadge(position, unreadCount);
            } else {
                badge[position].setBadgeNumber(unreadCount);
            }
        } else {
            if (badge[position] != null) {
                badge[position].hide(false);
            }
        }
        ShortcutBadger.applyCount(App.getAppContext(), App.getUnreadCount());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.compTopBarBottomNavigationActivity.llBackTopBarComp) {
            onBackPressed();
        } else if (v == binding.compTopBarBottomNavigationActivity.btRightButtonTopBarComp) {
            if (toolbarCallback != null) {
                toolbarCallback.clickTopBarRightTextButton(v);
            }
        } else if (v == binding.compTopBarBottomNavigationActivity.llRightButtonTopBarComp) {
            if (toolbarCallback != null) {
                toolbarCallback.clickTopBarRightImageButton(v);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startSignalRService();
    }
}
