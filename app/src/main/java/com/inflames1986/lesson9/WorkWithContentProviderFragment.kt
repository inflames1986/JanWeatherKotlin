package com.inflames1986.lesson9

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.databinding.FragmentWorkWithContentProviderBinding


class WorkWithContentProviderFragment : Fragment() {

    private var _binding: FragmentWorkWithContentProviderBinding? = null
    private val binding: FragmentWorkWithContentProviderBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkWithContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getContacts()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            explain()
        } else {
            mRequestPermission()
        }
    }

    val REQUEST_CODE_CONTACTS = 999
    val REQUEST_CODE_CALL = 998

    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ACCESS_MESSAGE_TITLE))
            .setMessage(getString(R.string.ACCESS_MESSAGE_BODY))
            .setPositiveButton(getString(R.string.ACCESS_GRANTED_BTN)) { _, _ ->
                mRequestPermission()
            }
            .setNegativeButton(getString(R.string.ACCESS_NOT_GRANTED_BTN)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun mRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_CONTACTS)
        requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_CONTACTS) {

            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.READ_CONTACTS && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    explain()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("Range")
    private fun getContacts() {
        context?.let { it ->
            val contentResolver = it.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let { cursor ->
                for (i in 0 until cursor.count) {
                    cursor.moveToPosition(i)
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val contactId =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val number = getNumberFromID(contentResolver, contactId)
                    addView(name, number)
                }
            }
            cursor?.close()
        }
    }

    private fun getNumberFromID(contRes: ContentResolver, contactId: String): String {
        val phones = contRes.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null
        )
        var number: String = "none"
        phones?.let { cursor ->
            while (cursor.moveToNext()) {
                number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        phones?.close()
        return number
    }

    private fun addView(name: String, number: String) {
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = "$name:$number"
            textSize = 30f
            setOnClickListener {
                numberCurrent = number
                makeCall()
            }
        })
    }

    private var numberCurrent: String = "none"
    private fun makeCall() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numberCurrent"))
            startActivity(intent)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkWithContentProviderFragment()
    }
}
