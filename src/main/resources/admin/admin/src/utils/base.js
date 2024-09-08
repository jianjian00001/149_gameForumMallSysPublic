const base = {
    get() {
                return {
            url : "http://localhost:8080/gameForum/",
            name: "gameForum",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/gameForum/front/index.html'
        };
            },
    getProjectName(){
        return {
            projectName: "游戏论坛平台"
        }
    }
}
export default base
