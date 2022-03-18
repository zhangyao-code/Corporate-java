<template>
    <page-container title="Page 1" sub-title="is a sub-title." :tabList="[{key: 'tab1', tab: 'Tab 1'}, {key: 'tab2', tab: 'Tab 2'}]">
        <template #extra>
            <div>
                container.content
            </div>
        </template>
        <span>page-content</span>
    </page-container>
</template>
