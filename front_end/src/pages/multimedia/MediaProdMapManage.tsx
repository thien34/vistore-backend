import useMediaProdMapManage from './MediaProdMapManage.vm'
import { Table, Button, Collapse, Tabs, Form, Input, Modal, InputNumber, Switch, Upload, Empty, Card } from 'antd'
import { PlusOutlined, UploadOutlined } from '@ant-design/icons'
import AppActions from '@/constants/AppActions'

export default function MediaProdMapManage() {
    const {
        filter,
        handleTableChange,
        isLoadingPicture,
        columnsProductPicture,
        listPictureResponse,
        form,
        editForm,
        isEditModalVisible,
        editRecord,
        isEditPicture,
        setIsEditPicture,
        validateURL,
        handleCancelEdit,
        handleUpdateProductPicture,
        columnsProductVideo,
        isLoadingMultimedia,
        handleUpdateProductVideo,
        listMultimediaResponse,
        handleAddUrlVideo,
        fileList,
        isUploadMode,
        uploading,
        propsUploadPicture,
        handleAddUploadPicture,
        propsUpload,
        handleSwitchChange,
        handleAddUploadVideo,
        loading,
    } = useMediaProdMapManage()
    return (
        <>
            <Collapse
                collapsible='header'
                defaultActiveKey={['1']}
                className='mb-6'
                items={[
                    {
                        key: '1',
                        label: 'Multimedia',
                        children: (
                            <Tabs
                                defaultActiveKey='1'
                                type='card'
                                size='middle'
                                items={[
                                    {
                                        key: '1',
                                        label: 'Pictures',
                                        children: (
                                            <>
                                                <Table
                                                    columns={columnsProductPicture}
                                                    dataSource={listPictureResponse?.items}
                                                    pagination={{
                                                        current: filter.pageNo ?? 1,
                                                        pageSize: filter.pageSize ?? 6,
                                                        total:
                                                            (listPictureResponse?.totalPages ?? 0) *
                                                            (filter.pageSize ?? 6),
                                                        onChange: (page, pageSize) =>
                                                            handleTableChange({
                                                                current: page,
                                                                pageSize: pageSize,
                                                            }),
                                                    }}
                                                    scroll={{ x: 900 }}
                                                    rowKey='id'
                                                    loading={isLoadingPicture}
                                                    bordered
                                                />
                                                <Card title='Add New Picture'>
                                                    <Form
                                                        initialValues={{ displayOrder: 0 }}
                                                        form={form}
                                                        layout='vertical'
                                                        onFinish={handleAddUploadPicture}
                                                        style={{ marginTop: '16px' }}
                                                    >
                                                        <Form.Item
                                                            label='Upload picture'
                                                            name='files'
                                                            rules={[
                                                                {
                                                                    required: true,
                                                                    message: 'Please upload an image file!',
                                                                },
                                                            ]}
                                                        >
                                                            <Upload
                                                                {...propsUploadPicture}
                                                                multiple
                                                                maxCount={5}
                                                                accept='image/*'
                                                                onChange={(info) => {
                                                                    if (info.fileList.length > 5) {
                                                                        info.fileList = info.fileList.slice(-5)
                                                                    }
                                                                }}
                                                                fileList={fileList}
                                                            >
                                                                <Button icon={<UploadOutlined />}>
                                                                    Click to Upload
                                                                </Button>
                                                            </Upload>
                                                        </Form.Item>
                                                        <Form.Item style={{ textAlign: 'center' }}>
                                                            <Button
                                                                icon={<PlusOutlined />}
                                                                size='large'
                                                                className='default-btn-color m-1'
                                                                htmlType='submit'
                                                                disabled={uploading}
                                                                loading={uploading}
                                                            >
                                                                Upload picture
                                                            </Button>
                                                        </Form.Item>
                                                    </Form>
                                                </Card>
                                                <Modal
                                                    title='Edit Picture'
                                                    visible={isEditPicture}
                                                    onCancel={() => setIsEditPicture(false)}
                                                >
                                                    <Form
                                                        form={form}
                                                        layout='vertical'
                                                        onFinish={handleUpdateProductPicture}
                                                    >
                                                        <Form.Item
                                                            label='Display Order'
                                                            name='displayOrder'
                                                            rules={[
                                                                {
                                                                    required: true,
                                                                    message: 'Please enter the display order!',
                                                                },
                                                                {
                                                                    type: 'number',
                                                                    min: 0,
                                                                    max: 2000000,
                                                                    message:
                                                                        'Display order must be between 0 and 2,000,000!',
                                                                },
                                                            ]}
                                                        >
                                                            <InputNumber size='large' />
                                                        </Form.Item>
                                                    </Form>
                                                </Modal>
                                            </>
                                        ),
                                    },
                                    {
                                        key: '2',
                                        label: 'Videos',
                                        children: (
                                            <>
                                                {listMultimediaResponse?.items?.length ? (
                                                    <Table
                                                        columns={columnsProductVideo}
                                                        dataSource={listMultimediaResponse.items}
                                                        pagination={{
                                                            current: filter.pageNo ?? 1,
                                                            pageSize: filter.pageSize ?? 6,
                                                            total:
                                                                (listMultimediaResponse?.totalPages ?? 0) *
                                                                (filter.pageSize ?? 6),
                                                            onChange: (page, pageSize) =>
                                                                handleTableChange({
                                                                    current: page,
                                                                    pageSize: pageSize,
                                                                }),
                                                        }}
                                                        scroll={{ x: 900 }}
                                                        rowKey='id'
                                                        loading={isLoadingMultimedia}
                                                        bordered
                                                    />
                                                ) : (
                                                    <div
                                                        style={{
                                                            display: 'flex',
                                                            justifyContent: 'center',
                                                        }}
                                                    >
                                                        <Empty
                                                            image='https://gw.alipayobjects.com/zos/antfincdn/ZHrcdLPrvN/empty.svg'
                                                            imageStyle={{ height: 60 }}
                                                        />
                                                    </div>
                                                )}
                                                <Card title='Add New Video'>
                                                    <div style={{ marginBottom: '16px' }}>
                                                        <Switch
                                                            checked={isUploadMode}
                                                            onChange={handleSwitchChange}
                                                            checkedChildren='Upload video'
                                                            unCheckedChildren='Embed video URL'
                                                        />
                                                    </div>
                                                    {isUploadMode ? (
                                                        <Form
                                                            initialValues={{ displayOrder: 0 }}
                                                            form={form}
                                                            layout='vertical'
                                                            onFinish={handleAddUploadVideo}
                                                            style={{ marginTop: '16px' }}
                                                        >
                                                            <Form.Item
                                                                label='Upload video'
                                                                name='videoFile'
                                                                rules={[
                                                                    {
                                                                        required: true,
                                                                        message: 'Please upload a video file!',
                                                                    },
                                                                ]}
                                                            >
                                                                <Upload {...propsUpload} maxCount={1} accept='video/*'>
                                                                    <Button icon={<UploadOutlined />}>
                                                                        Click to Upload
                                                                    </Button>
                                                                </Upload>
                                                            </Form.Item>
                                                            <Form.Item
                                                                label='Display order'
                                                                name='displayOrder'
                                                                tooltip='Set the display order'
                                                                rules={[
                                                                    {
                                                                        required: true,
                                                                        message: 'Please enter the display order!',
                                                                    },
                                                                    {
                                                                        type: 'number',
                                                                        min: 0,
                                                                        max: 2000000,
                                                                        message:
                                                                            'Display order must be between 0 and 2,000,000!',
                                                                    },
                                                                ]}
                                                            >
                                                                <InputNumber size='large' type='number' />
                                                            </Form.Item>
                                                            <Form.Item style={{ textAlign: 'center' }}>
                                                                <Button
                                                                    icon={<PlusOutlined />}
                                                                    size='large'
                                                                    className='default-btn-color m-1'
                                                                    htmlType='submit'
                                                                    disabled={uploading}
                                                                    loading={uploading}
                                                                >
                                                                    Upload video
                                                                </Button>
                                                            </Form.Item>
                                                        </Form>
                                                    ) : (
                                                        <Form
                                                            initialValues={{ displayOrder: 0 }}
                                                            form={form}
                                                            layout='vertical'
                                                            onFinish={handleAddUrlVideo}
                                                            style={{ marginTop: '16px' }}
                                                        >
                                                            <Form.Item
                                                                label='Embed video URL'
                                                                name='videoUrl'
                                                                rules={[
                                                                    {
                                                                        required: true,
                                                                        message: 'Please input the video URL!',
                                                                    },
                                                                    { validator: validateURL },
                                                                    {
                                                                        max: 100,
                                                                        message:
                                                                            'The URL cannot be longer than 100 characters!',
                                                                    },
                                                                ]}
                                                            >
                                                                <Input size='large' maxLength={100} />
                                                            </Form.Item>
                                                            <Form.Item
                                                                label='Display order'
                                                                name='displayOrder'
                                                                tooltip='Set the display order'
                                                                rules={[
                                                                    {
                                                                        required: true,
                                                                        message: 'Please enter the display order!',
                                                                    },
                                                                    {
                                                                        type: 'number',
                                                                        min: 0,
                                                                        max: 2000000,
                                                                        message:
                                                                            'Display order must be between 0 and 2,000,000!',
                                                                    },
                                                                ]}
                                                            >
                                                                <InputNumber size='large' type='number' />
                                                            </Form.Item>
                                                            <Form.Item style={{ textAlign: 'center' }}>
                                                                <Button
                                                                    icon={<PlusOutlined />}
                                                                    size='large'
                                                                    className='default-btn-color m-1'
                                                                    htmlType='submit'
                                                                >
                                                                    Add video URL
                                                                </Button>
                                                            </Form.Item>
                                                        </Form>
                                                    )}
                                                </Card>
                                            </>
                                        ),
                                    },
                                ]}
                            ></Tabs>
                        ),
                    },
                ]}
            ></Collapse>
            <Modal title='Edit Product Video' open={isEditModalVisible} footer={null} onCancel={handleCancelEdit}>
                {editRecord && (
                    <Form
                        form={editForm}
                        initialValues={editRecord}
                        layout='vertical'
                        onFinish={handleUpdateProductVideo}
                    >
                        <Form.Item>
                            <Switch
                                checked={isUploadMode}
                                onChange={handleSwitchChange}
                                checkedChildren='Upload video'
                                unCheckedChildren='Embed video URL'
                                disabled={!isUploadMode}
                            />
                        </Form.Item>

                        {isUploadMode ? (
                            <Form.Item
                                label='Upload video'
                                name='videoFile'
                                rules={[{ required: true, message: 'Please upload a video file!' }]}
                            >
                                <Upload {...propsUpload} maxCount={1} accept='video/*' fileList={fileList}>
                                    <Button icon={<UploadOutlined />}>Click to Upload</Button>
                                </Upload>
                            </Form.Item>
                        ) : (
                            <Form.Item
                                label='Embed video URL'
                                name='videoUrl'
                                rules={[
                                    { required: true, message: 'Please input the video URL!' },
                                    { validator: validateURL },
                                ]}
                            >
                                <Input />
                            </Form.Item>
                        )}
                        <Form.Item
                            label='Display order'
                            name='displayOrder'
                            rules={[{ required: true, message: 'Please input the display order!' }]}
                        >
                            <Input type='number' />
                        </Form.Item>
                        <Form.Item>
                            <Button loading={loading} type='primary' htmlType='submit'>
                                {AppActions.EDIT}
                            </Button>
                        </Form.Item>
                    </Form>
                )}
            </Modal>
        </>
    )
}
