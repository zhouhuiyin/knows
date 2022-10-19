let questionApp=new Vue({
    el:"#questionApp",
    data:{
        question:{}
    },
    methods:{
        loadQuestion:function(){
            // question/detail_teacher.html?149
            // 我们要获得url地址栏中?之后的id值
            let qid=location.search;
            // location.search可以获得地址栏中?之后的内容,具体获取规则如下
            // 如果url中没有? qid=""
            // 如果url中有?但是?之后没有任何内容 qid=""
            // 如果url中有?而且?之后有内容(例如149) qid="?149"
            // 既question/detail_teacher.html?149   ->  qid=?149
            if(!qid){
                // !qid就是判断qid不存在,或理解为qid没有值
                alert("请指定问题的id");
                return;
            }
            //  qid   ?149  -> 149
            qid=qid.substring(1);
            axios({
                url:"/v1/questions/"+qid,
                method:"get"
            }).then(function(response){
                questionApp.question=response.data;
                addDuration(response.data);
            })
        }
    },
    created:function(){
        this.loadQuestion()
    }
})

let postAnswerApp=new Vue({
    el:"#postAnswerApp",
    data:{},
    methods: {
        postAnswer:function(){
            // 要想提交表单,需要问题id和回答内容
            // 问题id就是url地址栏?之后的内容
            let qid=location.search;
            if(!qid){
                alert("qid没有值!!!!");
                return;
            }
            qid=qid.substring(1);
            // 使用jquery代码获得富文本编辑器中的内容
            let content=$("#summernote").val();
            // 创建表单
            let form=new FormData();
            form.append("questionId",qid);
            form.append("content",content);
            axios({
                url:"/v1/answers",
                method:"post",
                data:form
            }).then(function(response){
                console.log(response.data);
                let answer=response.data;
                // 设置answer的duration持续时间属性为"刚刚"
                answer.duration="刚刚";
                // 将新增成功的对象新增到回答列表中
                answersApp.answers.push(answer);
                // 利用富文本编辑器提供的方法,重置其中内容
                $("#summernote").summernote("reset");
            })
        }
    }
})

let answersApp=new Vue({
    el:"#answersApp",
    data:{
        answers:[]
    },
    methods:{
        loadAnswers:function(){
            // 这个方法也需要问题的id,就在url?之后
            let qid=location.search;
            if(!qid){
                return;
            }
            qid=qid.substring(1);
            axios({
                url:"/v1/answers/question/" + qid,
                method:"get"
            }).then(function(response){
                answersApp.answers=response.data;
                answersApp.updateDuration();
            })
        },
        updateDuration:function(){
            let answers=this.answers;
            for(let i=0;i<answers.length;i++){
                addDuration(answers[i]);
            }
        },
        postComment:function(answerId){
            // 新增评论需要answerId和评论内容content
            // 参数answerId已经提供,需要我们编码获取的就是content
            // 使用jquery的后代选择器获得textarea对象
            let textarea=$("#addComment"+answerId+" textarea");
            let content=textarea.val();
            // 创建表单
            let form=new FormData();
            form.append("answerId",answerId);
            form.append("content",content);
            axios({
                url:"/v1/comments",
                method:"post",
                data:form
            }).then(function(response){
                // 获得新增成功的评论
                let comment=response.data;
                // 获得所有回答
                let answers=answersApp.answers;
                // 遍历所有回答
                for(let i=0;i<answers.length;i++){
                    // 判断当前回答的id是否和新增评论的answerId一致
                    if(answers[i].id==answerId){
                        // 如果一致,表示新增的评论对象就是当前回答的
                        // 所以添加到当前回答的评论列表中
                        answers[i].comments.push(comment);
                        // 清空输入评论框中的内容
                        textarea.val("");
                        break;
                    }
                }
            })
        },
        removeComment:function(commentId,index,comments){
            axios({
                url:"/v1/comments/"+commentId+"/delete",
                method:"get"
            }).then(function(response){
                console.log(response.data);
                if(response.data=="ok"){
                    // splice表示要从数组对象中删除元素
                    // 两个参数([删除的起始下标],[删除的个数])
                    comments.splice(index,1);
                }
            })
        },
        updateComment:function(commentId,index,answer){
            //需要获得修改后的评论内容,使用后代选择器获得对象和评论内容
            let textarea=$("#editComment"+commentId+" textarea");
            let content=textarea.val();
            // 如果内容是空就不修改了
            if(!content){
                return;
            }
            // 创建表单,封装CommentVO对象的数据
            // 这里利用SpringValidation验证,所以answerId也要传过去,但是并不使用
            let form=new FormData();
            form.append("answerId",answer.id);
            form.append("content",content);
            axios({
                url:"/v1/comments/"+commentId+"/update",
                method:"post",
                data:form
            }).then(function(response){
                console.log("使用typeof判断返回值的类型:"
                                    +typeof(response.data))
                if(typeof(response.data)=="object") {
                    // 控制器返回的是修改成功后的评论对象,直接获取
                    let comment = response.data;
                    // 本次修改操作没有变化数组元素的数量(长度没变化)
                    // 数组元素数量不变化时,Vue不会自动更新页面上的内容
                    // 既然它不会自动修改,就需要我们手动修改
                    // Vue提供了我们手动修改元素内容的方法,
                    // 这个方法引起的变化会显示在页面上
                    // Vue.set([要修改的数组],[要修改的元素下标],[修改成什么])
                    Vue.set(answer.comments, index, comment);
                    // 修改成功后,编辑框自动收缩
                    $("#editComment" + commentId).collapse("hide");
                }else{
                    alert(response.data);
                }
            })
        },
        answerSolved:function(answerId){
            axios({
                url:"/v1/answers/"+answerId+"/solved",
                method:"get"
            }).then(function(response){
                console.log(response.data);
            })
        }
    },
    created:function(){
        this.loadAnswers();
    }
})

