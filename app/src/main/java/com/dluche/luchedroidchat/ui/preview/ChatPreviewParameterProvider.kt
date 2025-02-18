package com.dluche.luchedroidchat.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.fake.chat1
import com.dluche.luchedroidchat.model.fake.chat2
import com.dluche.luchedroidchat.model.fake.chat3

class ChatPreviewParameterProvider : PreviewParameterProvider<Chat> {
    override val values: Sequence<Chat> = sequenceOf(
        chat1,
        chat2,
        chat3
    )
}