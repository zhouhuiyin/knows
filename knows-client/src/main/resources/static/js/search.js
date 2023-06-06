
/*
显示当前用户的问题
 */
let questionsApp = new Vue({
    el:'#questionsApp',
    data: {
        questions:[],
        pageinfo:{},
    },
    methods: {
        loadQuestions:function (pageNum) {
            if(! pageNum){
                pageNum = 1;
            }
            let key = location.search;
            if(!key){
                return;
            }
            // decodeURI是解码方法,能够将Unicode格式的中文,转回成可识别的中文文字
            key=decodeURI(key.substring(1));
            let form=new FormData();
            form.append("pageNum",pageNum);
            form.append("accessToken",token);
            form.append("key",key);
            axios({
                url: 'http://localhost:9000/v3/questions',
                method: "post",
                data: form
            }).then(function(r){
                console.log("成功加载数据");
                console.log(r);
                if(r.status == OK){
                    questionsApp.questions = r.data.list;
                    questionsApp.pageinfo = r.data;
                    //为question对象添加持续时间属性
                    questionsApp.updateDuration();
                    questionsApp.updateTagImage();
                    document.documentElement.scrollTop = 0
                }
            })
        },
        updateTagImage:function(){
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
               let tags = questions[i].tags;
               // 判断tags!=null 或理解为判断tags存在
               if(tags){
                   let tagImage = '/img/tags/'+tags[0].id+'.jpg';
                   console.log(tagImage);
                   questions[i].tagImage = tagImage;
               }
            }
        },
        updateDuration:function () {
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
                //创建问题时候的时间毫秒数
                let createtime = new Date(questions[i].createtime).getTime();
                //当前时间毫秒数
                let now = new Date().getTime();
                let duration = now - createtime;
                if (duration < 1000*60){ //一分钟以内
                    questions[i].duration = "刚刚";
                }else if(duration < 1000*60*60){ //一小时以内
                    questions[i].duration =
                        (duration/1000/60).toFixed(0)+"分钟以前";
                }else if (duration < 1000*60*60*24){
                    questions[i].duration =
                        (duration/1000/60/60).toFixed(0)+"小时以前";
                }else {
                    questions[i].duration =
                        (duration/1000/60/60/24).toFixed(0)+"天以前";
                }
            }
        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions(1);

    }
});










