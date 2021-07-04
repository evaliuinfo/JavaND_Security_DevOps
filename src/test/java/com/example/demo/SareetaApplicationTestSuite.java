package com.example.demo;


import com.example.demo.controllers.TestCartController;
import com.example.demo.controllers.TestItemController;
import com.example.demo.controllers.TestOrderController;
import com.example.demo.controllers.TestUserController;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {TestCartController.class, TestItemController.class, TestOrderController.class, TestUserController.class}
)
public class SareetaApplicationTestSuite {
}
