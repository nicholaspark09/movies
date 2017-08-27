package com.example.movies

import android.os.Bundle
import com.example.movies.ui.MoviesFragment
import com.example.vn008xw.golf.ui.base.BaseActivity


class MovieActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie)

    var fragment = supportFragmentManager.findFragmentById(R.id.content)
    if (fragment == null) fragment = MoviesFragment.newInstance()
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.content, fragment)
        .commitAllowingStateLoss()
  }
}