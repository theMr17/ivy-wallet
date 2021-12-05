package com.ivy.wallet.compose.scenario

import com.ivy.wallet.compose.IvyComposeTest
import com.ivy.wallet.compose.helpers.*
import com.ivy.wallet.model.LoanType
import com.ivy.wallet.ui.theme.Blue
import com.ivy.wallet.ui.theme.Ivy
import com.ivy.wallet.ui.theme.Purple2
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class LoansTest : IvyComposeTest() {

    private val onboarding = OnboardingFlow(composeTestRule)
    private val homeMoreMenu = HomeMoreMenu(composeTestRule)
    private val loansScreen = LoansScreen(composeTestRule)
    private val loanModal = LoanModal(composeTestRule)
    private val loanDetailsScreen = LoanDetailsScreen(composeTestRule)

    @Test
    fun CreateLoan() = testWithRetry {
        onboarding.quickOnboarding()

        homeMoreMenu.clickOpenCloseArrow()
        homeMoreMenu.clickLoans()

        loansScreen.addLoan()
        loanModal.apply {
            enterName("Loan 1")
            enterAmount("4,800.32")
            selectLoanType(LoanType.BORROW)
            colorPicker.chooseColor(Purple2)
            chooseIconFlow.chooseIcon("education")

            clickAdd()
        }

        loansScreen.assertLoan(
            name = "Loan 1",
            amount = "4,800",
            amountDecimal = ".32",
            loanType = LoanType.BORROW,
            currency = "USD",
            amountPaid = "0.00",
            percentPaid = "0.00"
        )
    }

    @Test
    fun EditLoan() = testWithRetry {
        onboarding.quickOnboarding()

        homeMoreMenu.clickOpenCloseArrow()
        homeMoreMenu.clickLoans()

        loansScreen.addLoan()
        loanModal.apply {
            enterName("Razer Blade")
            enterAmount("4,800")
            selectLoanType(LoanType.LEND)
            colorPicker.chooseColor(Blue)
            chooseIconFlow.chooseIcon("star")

            clickAdd()
        }
        loansScreen.assertLoan(
            name = "Razer Blade",
            amount = "4,800",
            amountDecimal = ".00",
            loanType = LoanType.LEND,
            currency = "USD",
            amountPaid = "0.00",
            percentPaid = "0.00"
        )
        //--------------- Preparation --------------------------------------------------------------

        //Edit Loan
        loansScreen.clickLoan(
            loanName = "Razer Blade"
        )
        loanDetailsScreen.clickEdit()
        loanModal.apply {
            enterAmount("4,000.25")
            enterName("Laptop")
            chooseIconFlow.chooseIcon("account")
            colorPicker.chooseColor(Ivy)
            selectLoanType(LoanType.BORROW)

            clickSave()
        }

        //Verify edit in LoanDetails
        loanDetailsScreen.apply {
            assertLoanName(loanName = "Laptop")
            assertLoanAmount(
                amount = "4,000",
                amountDecimal = ".25"
            )
            assertAmountPaid(
                amountPaid = "0.00",
                loanAmount = "4,000.25"
            )
            assertPercentPaid(percentPaid = "0.00%")
            assertLeftToPay(leftToPayAmount = "4,000.25")

            clickClose()
        }

        //Verify edit in Loans screen
        loansScreen.assertLoan(
            name = "Laptop",
            loanType = LoanType.BORROW,
            amount = "4,000",
            amountDecimal = ".25",
            amountPaid = "0.00",
            percentPaid = "0.00"
        )
    }

    @Test
    fun CreateSeveralLoans() {

    }

    @Test
    fun DeleteLoanWithNoRecrods() {

    }

    //Loan records ---------------------------------------------------------------------------------
    @Test
    fun AddLoanRecord() {

    }

    @Test
    fun EditLoanRecord() {

    }

    @Test
    fun DeleteLoanRecord() {

    }

    @Test
    fun AddSeveralLoanRecords() {

    }

    @Test
    fun DeleteLoanWithRecrods() {

    }

    //Corner cases
    @Test
    fun OverpayLoan() {

    }
}