import {
  ChangeDetectionStrategy,
  Component,
  inject,
  Input,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';

/**
 * @title Dialog elements
 */
@Component({
  selector: 'dialog-elements',
  templateUrl: './dialog-element.html',
  styleUrl: 'diaglog-element.css',
  imports: [MatButtonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogElement {
  readonly dialog = inject(MatDialog);
  @Input() disabled: boolean;
  @Input() isSuccess: boolean;
  constructor() {
    this.disabled = false;
    this.isSuccess = false;
  }

  openDialog() {
    this.dialog.open(DialogElementsDialog);
  }
}

@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: './dialog-elements-dialog.html',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogElementsDialog {}

/**  Copyright 2025 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at https://angular.io/license */
