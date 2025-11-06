package com.cdlzrn.kwave.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.BottomNavBarItem
import com.cdlzrn.kwave.presentation.asset.BottomNavBar
import com.cdlzrn.kwave.presentation.asset.Oops
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.chat.ChatScreen
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.home.HomeScreen
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.profile.ProfileScreen
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.search.SearchScreen
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.ShopHome
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.ShopProduct
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen.EmptyCart
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen.FillCart
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen.Payment
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen.PaymentSuccess
import com.cdlzrn.kwave.presentation.theme.ui.Gray100

@SuppressLint("AutoboxingStateCreation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation( modifier: Modifier = Modifier ) {

    val items: List<BottomNavBarItem> = listOf(
        BottomNavBarItem(
            imageSelected = painterResource(R.drawable.logo_mini),
            imageUnselected = painterResource(R.drawable.logo_mini_uncolored),
            label = "Главная",
            route = Home,
            selectedRoutes = listOf(Home::class)
        ),
        BottomNavBarItem(
            imageSelected = painterResource(R.drawable.search),
            imageUnselected = painterResource(R.drawable.search_uncolored),
            label = "Поиск",
            route = Search,
            selectedRoutes = listOf(Search::class)
        ),
        BottomNavBarItem(
            imageSelected = painterResource(R.drawable.chat),
            imageUnselected = painterResource(R.drawable.chat_uncolored),
            label = "Чат",
            route = Chat,
            selectedRoutes = listOf(Chat::class)
        ),
        BottomNavBarItem(
            imageSelected = painterResource(R.drawable.shop),
            imageUnselected = painterResource(R.drawable.shop_uncolored),
            label = "Магазин",
            route = ShopHome,
            selectedRoutes = listOf(
                ShopHome::class, CartActivities::class, ShopEmptyCart::class, ShopFillCart::class,
                ShopCartPayment::class, ShopCartThanks::class, ShopProduct::class
            )
        ),
        BottomNavBarItem(
            imageSelected = painterResource(R.drawable.profile),
            imageUnselected = painterResource(R.drawable.profile_uncolored),
            label = "Профиль",
            route = Profile,
            selectedRoutes = listOf(Profile::class)
        ),
    )

    val navController = rememberNavController()
    var isBottomNavBarShow by remember { mutableStateOf( false ) }

    var isShowOops by remember { mutableStateOf(false) }
    val setShowOops: (Boolean) -> Unit = { show ->
        isShowOops = show
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isShowOops,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray100.copy(alpha = 0.5f))
                        .pointerInput(Unit) {
                            detectTapGestures {
                                isShowOops = false
                            }
                        }
                )
            }

            AnimatedVisibility(
                visible = isShowOops,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Oops(
                    navToRegister = {
                        isShowOops = false
                        navController.navigate(Register){
                            popUpTo(CreateOrLogin)
                        }
                    },
                    navToLogin = {
                        isShowOops = false
                        navController.navigate(Login){
                            popUpTo(CreateOrLogin)
                        }
                    },
                    clickOnClose = { isShowOops = false }
                )
            }
        }

        Column{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AuthActivities
                ) {
                    navigation<AuthActivities>(
                        startDestination = Splash
                    ) {
                        composable<Splash> {
                            Splash(
                                navToNext = {
                                    navController.navigate(CreateOrLogin) {
                                        popUpTo(Splash) {
                                            inclusive = true
                                        }
                                    }
                                },
                                navToHome = {
                                    navController.navigate(Home) {
                                        popUpTo(AuthActivities) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable<CreateOrLogin> {
                            CreateOrLogin(
                                navigateToLogin = { navController.navigate(Login) },
                                navigateToCreate = { navController.navigate(Register) },
                                navigateToIncognito = { navController.navigate(Home) }
                            )
                        }
                        composable<Register> {
                            isBottomNavBarShow = false
                            Register(
                                navigateToInterests = {
                                    navController.navigate(Interests) {
                                        popUpTo(
                                            Register
                                        )
                                    }
                                },
                                navigateToLogin = {
                                    navController.navigate(Login) {
                                        popUpTo(
                                            CreateOrLogin
                                        )
                                    }
                                }
                            )
                        }
                        composable<Login> {
                            isBottomNavBarShow = false
                            Login(
                                navigateToRegister = {
                                    navController.navigate(Register) {
                                        popUpTo(
                                            CreateOrLogin
                                        )
                                    }
                                },
                                navigateToMain = {
                                    navController.navigate(Home) {
                                        popUpTo(
                                            AuthActivities
                                        ) { inclusive = true }
                                    }
                                },
                                onClickForgotPassword = {},
                            )
                        }
                        composable<Interests> {
                            Interests(
                                navigateToMain = {
                                    navController.navigate(Home) {
                                        popUpTo(
                                            AuthActivities
                                        ) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }

                    navigation<MainActivities>(
                        startDestination = Home
                    ) {

                        composable<Home> {
                            isBottomNavBarShow = true
                            HomeScreen(
                                navToShop = {
                                    navController.navigate(ShopHome) {
                                        popUpTo(Home) {
                                            inclusive = true
                                        }
                                    }
                                },
                                navToProduct = { productId ->
                                    navController.navigate(ShopProduct(productId = productId))
                                },
                                setShowOops = setShowOops
                            )
                        }
                        composable<Search> { SearchScreen() }
                        composable<Chat> { ChatScreen() }
                        composable<Profile> {
                            ProfileScreen(
                                navToCreateOrLogin = {
                                    navController.navigate(CreateOrLogin) {
                                        popUpTo(
                                            AuthActivities
                                        ) { inclusive = true }
                                    }
                                },
                                setVisibleBottomBar = { isBottomNavBarShow = it }
                            )
                        }

                        navigation<ShopActivities>(
                            startDestination = ShopHome
                        ) {
                            composable<ShopHome> {
                                ShopHome(
                                    navToEmptyCart = { navController.navigate(ShopEmptyCart) },
                                    navToFillCart = { navController.navigate(ShopFillCart) },
                                    navToProduct = { productId ->
                                        navController.navigate(ShopProduct(productId = productId))
                                    },
                                    setShowOops = setShowOops
                                )
                            }

                            composable<ShopProduct> {
                                val productId = it.toRoute<ShopProduct>().productId
                                ShopProduct(
                                    productID = productId,
                                    navToHome = { navController.popBackStack() },
                                    navToEmptyCart = { navController.navigate(ShopEmptyCart) },
                                    navToFillCart = { navController.navigate(ShopFillCart) },
                                    setShowOops = setShowOops
                                )
                            }

                            navigation<CartActivities>(
                                startDestination = ShopEmptyCart
                            ) {
                                composable<ShopEmptyCart> {
                                    EmptyCart(
                                        navToShopHome = {
                                            navController.navigate(ShopHome) {
                                                popUpTo(
                                                    ShopHome
                                                ) { inclusive = true }
                                            }
                                        }
                                    )
                                }

                                composable<ShopFillCart> {
                                    FillCart(
                                        navToShopHome = {
                                            navController.navigate(ShopHome) {
                                                popUpTo(
                                                    ShopHome
                                                ) { inclusive = true }
                                            }
                                        },
                                        navToPayment = { navController.navigate(ShopCartPayment) },
                                        navToEmptyCart = {
                                            navController.navigate(ShopEmptyCart) {
                                                popUpTo(
                                                    ShopHome
                                                ) { inclusive = true }
                                            }
                                        },
                                        navToProduct = { productId ->
                                            navController.navigate(ShopProduct(productId))
                                        },
                                    )
                                }

                                composable<ShopCartPayment> {

                                    DisposableEffect(Unit) {
                                        isBottomNavBarShow = false

                                        onDispose {
                                            isBottomNavBarShow = true
                                        }
                                    }

                                    Payment(
                                        navToThanks = {
                                            navController.navigate(ShopCartThanks) {
                                                popUpTo(
                                                    ShopCartThanks
                                                ) { inclusive = true }
                                            }
                                        },
                                        navToBack = { navController.popBackStack() },
                                    )
                                }

                                composable<ShopCartThanks> {
                                    PaymentSuccess(
                                        navToShopHome = {
                                            navController.navigate(ShopHome) {
                                                popUpTo(
                                                    ShopHome
                                                ) { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = isBottomNavBarShow
            ) {
                BottomNavBar(
                    navController = navController,
                    items = items,
                    setShowOops = setShowOops
                )
            }
        }
    }
}