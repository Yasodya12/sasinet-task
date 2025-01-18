import {createSlice} from "@reduxjs/toolkit";

const userSlice = createSlice({
    name: 'user',
    initialState: {
        id: null,
        email: null,
    },
    reducers: {
        setUser(state, action) {
            state.id = action.payload.id;
            state.email = action.payload.email;
        },
    },
});

export const { setUser } = userSlice.actions;
export default userSlice.reducer;
