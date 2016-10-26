package com.example.tablenjoy.helloreactiveworld;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.txt_content)
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //butteknife 주입
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_d, R.id.btn_j, R.id.btn_l})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_d:
                defaultStart();
                break;
            case R.id.btn_j:
                justStart();
                break;
            case R.id.btn_l:
                lambdaStart();
                break;
        }
    }

    private void lambdaStart() {
        Observable
                .just("Just lambda RxAndroid!!")
                .compose(bindToLifecycle())
                .subscribe(s->{
                   txtContent.setText(s);
                });
    }

    private void justStart() {
        Observable.just("Just RxAndroid!!")
                .compose(this.<String>bindToLifecycle())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        txtContent.setText(s);
                    }
                });
    }

    private void defaultStart() {
        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                        subscriber.onNext("Hello RxAndroid!!");
                        subscriber.onCompleted();

                    }
                });

        simpleObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                txtContent.setText(s);


            }
        });

    }

}
