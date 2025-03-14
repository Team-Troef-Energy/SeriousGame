import { Subject } from 'rxjs';
import { PopupProperties } from '../objects/PopupProperties';

class PopupState {
    private popup: Subject<PopupProperties>;

    constructor() {
        this.popup = new Subject<PopupProperties>(); 
    }

    popupEvent() {
        return this.popup.asObservable();
    }

    setPopup(properties: PopupProperties) {
        this.popup.next(properties);
    }
}

export default new PopupState();