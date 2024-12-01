# SpeechMate - 智能语音助手

## 项目概述
SpeechMate是一款智能语音转写及优化应用，致力于为用户提供高效的语音转文字服务，并通过AI技术对文本进行智能润色和主题分析。

## 核心功能
1. **语音录制**
   - 支持实时录音功能
   - 录音状态可视化
   - 录音文件本地存储

2. **语音转写**
   - 集成Silicon Flow API进行语音识别
   - 实时展示转写结果
   - 支持多种音频格式

3. **AI文本优化**
   - 智能文本润色
   - 错别字纠正
   - 语义优化
   - 主题标签生成
   - 相关话题建议

## 技术架构
- 开发语言：Java
- 架构模式：MVVM
- 数据存储：Room Database
- 网络请求：Retrofit + OkHttp
- 依赖注入：Hilt
- UI组件：XML布局 + Material Design
- 异步处理：RxJava
- 音频处理：Android MediaRecorder

## 界面设计
1. **首页（HomeScreen）**
   - 录音按钮
   - 原始转写文本展示区
   - AI优化文本展示区
   - 标签展示区

2. **历史记录（HistoryScreen）**
   - 时间线式布局
   - 录音文件播放功能
   - 转写文本查看
   - AI优化文本查看
   - 支持搜索和筛选

3. **个人中心（ProfileScreen）**
   - 用户设置
   - API配置
   - 存储管理
   - 主题设置

## 开发计划
1. **第一阶段：基础架构搭建**
   - 项目初始化
   - 依赖配置
   - 基础架构搭建
   - 数据库设计

2. **第二阶段：核心功能开发**
   - 录音功能实现
   - API集成
   - 语音转写功能
   - 文本优化功能

3. **第三阶段：UI实现**
   - 首页开发
   - 历史记录页面开发
   - 个人中心开发
   - UI优化

4. **第四阶段：功能完善**
   - 本地存储优化
   - 性能优化
   - 用户体验优化
   - Bug修复

## API集成
- 语音转写API：Silicon Flow Audio Transcriptions API
- 文本优化API：Silicon Flow Chat Completions API
- API密钥管理和安全存储

## 数据存储
### 本地数据库表设计
1. **录音记录表**
   - 录音ID
   - 录音文件路径
   - 录音时长
   - 创建时间
   
2. **转写记录表**
   - 转写ID
   - 录音ID（外键）
   - 原始转写文本
   - AI优化文本
   - 标签列表
   - 创建时间

## 下一步计划
1. 初始化项目结构
2. 配置必要的依赖
3. 实现基础UI框架
4. 开发录音功能模块

## 技术难点
1. 音频录制质量优化
2. API调用的错误处理
3. 大文本数据的存储和展示
4. 离线功能支持

## 项目维护
- 代码版本控制：Git
- 文档维护：Markdown
- 依赖管理：Gradle 
